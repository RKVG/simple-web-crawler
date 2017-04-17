package com.danielc.web.crawler.repository;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;

import static com.danielc.web.crawler.util.URLFormatHelper.cleanUrl;

public class HashSetUrlRepository implements UrlRepository {

  private Set<String> visitedUrls = Sets.newHashSet();
  private Set<String> unvisitedUrls = Sets.newHashSet();

  @Override
  public boolean storeVisitedUrl(String visitedUrl) {
    return visitedUrls.add(cleanUrl(visitedUrl));
  }

  @Override
  public boolean storeUnvisitedUrl(String unvisitedUrl) {
    return unvisitedUrls.add(cleanUrl(unvisitedUrl));
  }

  @Override
  public boolean refreshUnvisitedUrls(Collection<String> unvisitedUrls) {
    this.unvisitedUrls = Sets.newHashSet(unvisitedUrls);
    return true;
  }

  @Override
  public Set<String> findAllUnvisitedUrls() {
    return unvisitedUrls;
  }

  @Override
  public int countUnvisitedUrls() {
    return unvisitedUrls.size();
  }

  @Override
  public boolean isUrlVisited(String url) {
    return visitedUrls.contains(url);
  }

}
