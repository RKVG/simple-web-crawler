package com.danielc.web.crawler.service;

import com.danielc.web.crawler.repository.PageRepository;

public interface Printer {

  void setRepository(PageRepository pageRepository);

  void print();

}
