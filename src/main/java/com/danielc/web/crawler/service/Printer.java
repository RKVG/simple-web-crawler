package com.danielc.web.crawler.service;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.repository.PageRepository;

public interface Printer {

  void setConfig(AppConfig config);

  void setRepository(PageRepository pageRepository);

  void print();

}
