package com.danielc.web.crawler.service;

import com.danielc.web.crawler.collector.AssetUriCollector;
import com.danielc.web.crawler.collector.Collector;
import com.danielc.web.crawler.collector.NoWhitespaceCollector;
import com.danielc.web.crawler.collector.NonEmptyCollector;
import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.model.PageBuilder;
import com.danielc.web.crawler.repository.PageRepository;
import com.danielc.web.crawler.repository.UrlRepository;
import com.danielc.web.crawler.util.DomainMatcher;
import com.danielc.web.crawler.util.URLFormatHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.jsoup.Connection.Response;

public class JsoupCrawler implements Crawler {

  private static final Logger LOGGER = LoggerFactory.getLogger(JsoupCrawler.class);
  public static final String ACCEPTED_CONTENT_TYPE = "text/html";

  private Collector nonEmptyCollector = new NonEmptyCollector();
  private Collector noWhitespaceCollector = new NoWhitespaceCollector();
  private Collector assetUriCollector = new AssetUriCollector();

  private List<Collector> assetLinkCollectors = Lists.newArrayList(nonEmptyCollector, noWhitespaceCollector, assetUriCollector);
  private List<Collector> executableLinkCollectors = Lists.newArrayList(nonEmptyCollector, noWhitespaceCollector);

  private AppConfig config;
  private UrlRepository urlRepository;
  private PageRepository pageRepository;

  @Override
  public void setConfig(AppConfig config) {
    this.config = config;
  }

  @Override
  public void setRepositories(UrlRepository urlRepository, PageRepository pageRepository) {
    this.urlRepository = urlRepository;
    this.pageRepository = pageRepository;
  }

  @Override
  public void crawl(String baseUrl) {
    final DomainMatcher domainMatcher = new DomainMatcher(baseUrl);
    int visitedUrlCounter = 0;

    urlRepository.storeUnvisitedUrl(baseUrl);

    crawlingLoop:
    while (urlRepository.countUnvisitedUrls() > 0) {

      Collection<String> unvisitedUrls = urlRepository.findAllUnvisitedUrls();
      Set<String> uncleanedNewUrls = Sets.newHashSet();

      for (String url : unvisitedUrls) {

        try {
          LOGGER.debug("Crawling {} with requestTimeout={}, followRedirects={}", url, config.getCrawlerRequestTimeout(), config.isCrawlerFollowRedirects());

          Response response = Jsoup.connect(url)
            .timeout(config.getCrawlerRequestTimeout())
            .followRedirects(config.isCrawlerFollowRedirects())
            .ignoreContentType(true)
            .execute();

          if (response.statusCode() < 300 && response.contentType() != null && response.contentType().contains(ACCEPTED_CONTENT_TYPE)) {

            Document doc = response.parse();

            // Collect all executable links
            Set<String> links = collectLinkSet(doc.select("a[href],link[href]").stream().map(link -> link.attr("abs:href")).collect(toList()), executableLinkCollectors);

            List<String> assetLinks = assetUriCollector.collect(Lists.newArrayList(links)); // Some links might be asset, for example http://www.test.com/read-this.pdf
            List<String> executableLinks = links.stream().filter(link -> !assetLinks.contains(link)).collect(toList());

            // Collect all asset links
            Set<String> metaAssets = collectLinkSet(doc.select("meta[content]").stream().map(link -> link.attr("abs:content")).collect(toList()), assetLinkCollectors);
            Set<String> imageAssets = collectLinkSet(doc.select("img[src]").stream().map(link -> link.attr("abs:src")).collect(toList()), assetLinkCollectors);
            Set<String> scriptAssets = collectLinkSet(doc.select("script[src]").stream().map(link -> link.attr("abs:src")).collect(toList()), assetLinkCollectors);

            pageRepository.store(
              PageBuilder.newInstance()
                .url(url)
                .assets(metaAssets)
                .assets(imageAssets)
                .assets(scriptAssets)
                .assets(assetLinks)
                .build()
            );

            uncleanedNewUrls.addAll(executableLinks);

          } else if (response.statusCode() >= 300) {
            throw new HttpStatusException(response.statusMessage(), response.statusCode(), url);

          } else {
            throw new UnsupportedMimeTypeException(String.format("Unsupported mime type %s. Only accept %s", response.contentType(), ACCEPTED_CONTENT_TYPE), response.contentType(), url);

          }

        } catch (Exception e) {
          handleException(url, e);

        } finally {
          urlRepository.storeVisitedUrl(url);
          visitedUrlCounter++;

        }

        if (visitedUrlCounter >= config.getCrawlerMaxVisitedUrls()) {
          LOGGER.info("We have reached the predefined max number of pages to visit {}. Bye!", visitedUrlCounter);
          break crawlingLoop;
        }
      }

      urlRepository.refreshUnvisitedUrls(
        uncleanedNewUrls.stream()
          .map(URLFormatHelper::cleanUrl)
          .filter(link -> domainMatcher.matchedDomain(link) && !urlRepository.isUrlVisited(link))
          .collect(toSet())
      );

      LOGGER.debug("Visited=[{}], Unvisited=[{}]", visitedUrlCounter, urlRepository.countUnvisitedUrls());
    }
  }

  private Set<String> collectLinkSet(Collection<String> links, List<Collector> collectors) {
    List<String> results = Lists.newArrayList(links);

    for (Collector collector : collectors) {
      results = collector.collect(results);
    }

    return results.stream().collect(toSet());
  }

  private void handleException(String url, Exception e) {

    LOGGER.error("Exception when crawling url {}", url, e);

    pageRepository.store(
      PageBuilder.newInstance()
        .url(url)
        .error(getHttpStatus(e), e.getMessage())
        .build()
    );
  }

  private int getHttpStatus(Exception e) {
    if (e instanceof HttpStatusException) {
      return ((HttpStatusException) e).getStatusCode();
    }

    return 500;
  }

}
