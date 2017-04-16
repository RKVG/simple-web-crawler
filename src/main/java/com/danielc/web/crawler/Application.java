package com.danielc.web.crawler;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.model.Page;
import com.danielc.web.crawler.service.Crawler;
import com.danielc.web.crawler.service.GsonPrinter;
import com.danielc.web.crawler.service.JsoupCrawler;
import com.danielc.web.crawler.service.Printer;

import java.util.Collection;
import java.util.Scanner;

public class Application {

  private static final String INSTRUCTION = "Please enter a URL and press \"ENTER\" to continue...";

  public static void main(String[] args) {

//    String url = "http://www.google.de/?gfe_rd=cr&ei=DHjyWNyeCYfe8gfw94CQAg";
    AppConfig config = AppConfig.getInstance();

    config
      .load(null)
      .ifPresent(conf -> {

          System.out.println(INSTRUCTION);
          Scanner scanner = new Scanner(System.in);

          Crawler crawler = new JsoupCrawler(conf);
          Collection<Page> pages = crawler.crawl(scanner.nextLine());

          Printer printer = new GsonPrinter();
          printer.print(pages);

        }
      );

  }

}
