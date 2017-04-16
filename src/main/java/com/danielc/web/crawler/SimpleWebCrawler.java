package com.danielc.web.crawler;

import com.danielc.web.crawler.app.Application;
import com.danielc.web.crawler.app.ApplicationBuilder;
import com.danielc.web.crawler.config.PropertyConfig;
import com.danielc.web.crawler.service.GsonPrinter;
import com.danielc.web.crawler.service.JsoupCrawler;

public class SimpleWebCrawler {

  private static final String CUSTOM_PROPERTY = "config";

  public static void main(String[] args) {

    String configFile = System.getProperty(CUSTOM_PROPERTY);

    Application application = ApplicationBuilder.newInstance()
      .appConfig(PropertyConfig.getInstance(configFile))
      .crawler(new JsoupCrawler())
      .printer(new GsonPrinter())
      .build();

    application.run();

  }

}
