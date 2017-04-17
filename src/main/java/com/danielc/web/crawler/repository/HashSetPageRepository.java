package com.danielc.web.crawler.repository;

import com.danielc.web.crawler.model.Page;
import com.google.common.collect.Sets;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class HashSetPageRepository implements PageRepository {

  private Set<Page> visitedPages = Sets.newHashSet();

  @Override
  public boolean store(Page page) {
    return visitedPages.add(page);
  }

  @Override
  public Set<Page> findAll() {
    return visitedPages;
  }

  @Override
  public Set<Page> findAllWithoutError() {
    return visitedPages.stream()
      .filter(page -> !page.isInError())
      .sorted()
      .collect(toSet());
  }

  @Override
  public Set<Page> findAllInError() {
    return visitedPages.stream()
      .filter(Page::isInError)
      .sorted()
      .collect(toSet());
  }

  @Override
  public int countAll() {
    return findAll().size();
  }

  @Override
  public int countInError() {
    return findAllInError().size();
  }

}
