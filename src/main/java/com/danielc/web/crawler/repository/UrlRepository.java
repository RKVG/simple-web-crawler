package com.danielc.web.crawler.repository;

import java.util.Collection;
import java.util.Set;

public interface UrlRepository {

  boolean storeVisitedUrl(String visitedUrl);

  boolean storeUnvisitedUrl(String visitedUrl);

  boolean refreshUnvisitedUrls(Collection<String> unvisitedUrls);

  Collection<String> findAllUnvisitedUrls();

  int countUnvisitedUrls();

  boolean isUrlVisited(String url);

}
