package com.danielc.web.crawler.service;

import com.danielc.web.crawler.model.Page;

import java.util.Collection;

public interface Crawler {

  Collection<Page> crawl(String baseUrl);

}
