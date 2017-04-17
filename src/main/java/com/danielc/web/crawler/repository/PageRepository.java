package com.danielc.web.crawler.repository;

import com.danielc.web.crawler.model.Page;

import java.util.Collection;
import java.util.Set;

public interface PageRepository {

  boolean store(Page page);

  Collection<Page> findAll();

  Collection<Page> findAllWithoutError();

  Collection<Page> findAllInError();

  int countAll();

  int countInError();

}
