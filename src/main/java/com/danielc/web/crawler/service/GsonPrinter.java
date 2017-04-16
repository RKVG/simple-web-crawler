package com.danielc.web.crawler.service;

import com.danielc.web.crawler.model.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collection;

public class GsonPrinter implements Printer {

  private Gson gson;

  public GsonPrinter() {
    this.gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
  }

  @Override
  public void print(Collection<Page> pages) {
    System.out.println(gson.toJson(pages));
  }

}
