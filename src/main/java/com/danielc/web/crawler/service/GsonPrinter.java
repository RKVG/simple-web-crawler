package com.danielc.web.crawler.service;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.repository.PageRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.repeat;

public class GsonPrinter implements Printer {

  private static final int MARGIN = 30;
  private static final String PRINT_HEADING = "|%sCrawled %d page(s):%s|";
  private static final String ERROR_REPORT_HEADING = "|%sPages in ERROR [%d/%d]:%s|";

  private AppConfig config;
  private PageRepository pageRepository;
  private Gson gson;

  @Override
  public void setConfig(AppConfig config) {
    this.config = config;
  }

  @Override
  public void setRepository(PageRepository pageRepository) {
    this.pageRepository = pageRepository;
  }

  public GsonPrinter() {
    this.gson = new GsonBuilder()
      .disableHtmlEscaping()
      .setPrettyPrinting()
      .create();
  }

  @Override
  public void print() {

    int allCount = pageRepository.countAll();
    int errorCount = pageRepository.countInError();

    printHeading(format(PRINT_HEADING, repeat(" ", MARGIN), allCount, repeat(" ", MARGIN)));

    if (config.isPrinterExcludeErrors()) {
      System.out.println(gson.toJson(pageRepository.findAllWithoutError()));
    } else {
      System.out.println(gson.toJson(pageRepository.findAll()));
    }

    if (config.isPrinterReportErrors() && errorCount > 0) {
      printHeading(format(ERROR_REPORT_HEADING, repeat(" ", MARGIN), errorCount, allCount, repeat(" ", MARGIN)));
      System.out.println(gson.toJson(pageRepository.findAllInError()));
    }
  }

  private void printHeading(String heading) {
    System.out.println("\n" + repeat("=", heading.length()));
    System.out.println(heading);
    System.out.println(repeat("=", heading.length()));
  }

}
