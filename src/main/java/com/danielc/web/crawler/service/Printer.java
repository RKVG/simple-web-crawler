package com.danielc.web.crawler.service;

import com.danielc.web.crawler.model.Page;

import java.util.Collection;

public interface Printer {

  void print(Collection<Page> pages);

}
