package com.danielc.web.crawler.service;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.model.Page;

import java.util.Collection;

public interface Crawler {

  void setConfig(AppConfig config);

  Collection<Page> crawl(String baseUrl);

}
