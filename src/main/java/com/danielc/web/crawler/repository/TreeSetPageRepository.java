package com.danielc.web.crawler.repository;

import com.danielc.web.crawler.model.Page;
import com.google.common.collect.Sets;

import java.util.Set;

public class TreeSetPageRepository implements PageRepository {

  private Set<Page> visitedPages = Sets.newTreeSet();

  @Override
  public boolean store(Page page) {
    return visitedPages.add(page);
  }

  @Override
  public Set<Page> findAll() {
    return visitedPages;
  }

}
