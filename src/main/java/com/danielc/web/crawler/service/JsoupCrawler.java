package com.danielc.web.crawler.service;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.model.Page;
import com.danielc.web.crawler.util.URLFormatHelper;
import com.google.common.collect.Sets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Collection;
import java.util.Set;

import static com.danielc.web.crawler.util.URLFormatHelper.cleanUrl;
import static java.util.stream.Collectors.toSet;

public class JsoupCrawler implements Crawler {

  private AppConfig config;

  @Override
  public void setConfig(AppConfig config) {
    this.config = config;
  }

  @Override
  public Collection<Page> crawl(String baseUrl) {

    Set<String> visitedUrls = Sets.newHashSet();
    Set<String> unvisitedUrls = Sets.newHashSet();
    Set<Page> visitedPages = Sets.newHashSet();

    unvisitedUrls.add(baseUrl);

    while (unvisitedUrls.size() > 0) {

      Set<String> newUrls = Sets.newHashSet();

      for (String url : unvisitedUrls) {

        try {
          Document doc = Jsoup.connect(url)
            .followRedirects(config.getFollowRedirects())
            .timeout(config.getRequestTimeout())
            .get();

          Set<String> metaAssets = doc.select("meta[content]").stream().map(link -> link.attr("abs:content")).filter(URLFormatHelper::isAssetUrl).collect(toSet());
          Set<String> linksAssets = doc.select("link[href]").stream().map(link -> link.attr("abs:href")).filter(URLFormatHelper::isAssetUrl).collect(toSet());
          Set<String> imageAssets = doc.select("img[src]").stream().map(image -> image.attr("abs:src")).collect(toSet());
          Set<String> scriptAssets = doc.select("script[src]").stream().map(script -> script.attr("abs:src")).collect(toSet());

          visitedPages.add(
            Page
              .builder()
              .url(url)
              .assets(metaAssets)
              .assets(linksAssets)
              .assets(imageAssets)
              .assets(scriptAssets)
              .build()
          );

          visitedUrls.add(cleanUrl(url));

          newUrls.addAll(
            doc.select("a[href]").stream()
              .map(link -> cleanUrl(link.attr("abs:href")))
              .filter(link -> link.startsWith(doc.baseUri()) && !visitedUrls.contains(link))
              .collect(toSet())
          );

        } catch (Exception e) {
          e.printStackTrace();
        }

      }

      unvisitedUrls = newUrls;
    }

    return visitedPages;
  }

}
