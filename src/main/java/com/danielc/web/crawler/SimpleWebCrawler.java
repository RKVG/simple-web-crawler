package com.danielc.web.crawler;

import com.danielc.web.crawler.app.Application;
import com.danielc.web.crawler.app.ApplicationBuilder;
import com.danielc.web.crawler.config.PropertyConfig;
import com.danielc.web.crawler.service.GsonPrinter;
import com.danielc.web.crawler.service.JsoupCrawler;

public class SimpleWebCrawler {

  public static void main(String[] args) {

    Application application = ApplicationBuilder.newInstance()
      .appConfig(PropertyConfig.getInstance(null))
      .crawler(new JsoupCrawler())
      .printer(new GsonPrinter())
      .build();

    application.run();

  }

}
