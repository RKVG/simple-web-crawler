package com.danielc.web.crawler.service;

import com.danielc.web.crawler.model.Page;
import com.danielc.web.crawler.repository.PageRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collection;
import java.util.Set;

public class GsonPrinter implements Printer {

  private Gson gson;
  private PageRepository pageRepository;

  @Override
  public void setRepository(PageRepository pageRepository) {
    this.pageRepository = pageRepository;
  }

  public GsonPrinter() {
    this.gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
  }

  @Override
  public void print() {
    Set<Page> crawledPages = pageRepository.findAll();

    System.out.println(gson.toJson(crawledPages));
  }

}
