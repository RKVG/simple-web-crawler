package com.danielc.web.crawler.repository;

import com.danielc.web.crawler.model.Page;

import java.util.Set;

public interface PageRepository {

  boolean store(Page page);

  Set<Page> findAll();

}
