package com.danielc.web.crawler.app;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.service.Crawler;
import com.danielc.web.crawler.service.Printer;

import java.util.Scanner;

public class Application {

  private static final String INSTRUCTION = "Please enter a URL and press \"ENTER\" to continue...";

  AppConfig config;
  Crawler crawler;
  Printer printer;

  Application(AppConfig config, Crawler crawler, Printer printer) {
    this.config = config;
    this.crawler = crawler;
    this.printer = printer;
  }
  
  public void run() {
    System.out.println(INSTRUCTION);
    Scanner scanner = new Scanner(System.in);

    config
      .load()
      .ifPresent(conf -> {

        crawler.setConfig(conf);
        printer.setConfig(conf);

        crawler.crawl(scanner.nextLine());
        printer.print();

      });
  }

}
