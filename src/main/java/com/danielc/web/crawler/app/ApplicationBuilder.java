package com.danielc.web.crawler.app;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.service.Crawler;
import com.danielc.web.crawler.service.Printer;

public class ApplicationBuilder {

  private AppConfig appConfig;
  private Crawler crawler;
  private Printer printer;

  public static ApplicationBuilder newInstance() {
    return new ApplicationBuilder();
  }

  public ApplicationBuilder appConfig(AppConfig config) {
    this.appConfig = config;
    return this;
  }

  public ApplicationBuilder crawler(Crawler crawler) {
    this.crawler = crawler;
    return this;
  }

  public ApplicationBuilder printer(Printer printer) {
    this.printer = printer;
    return this;
  }

  public Application build() {
    if (this.appConfig == null) {
      throw new IllegalStateException("No application config found!");
    }

    if (crawler != null) {
      this.crawler.setConfig(this.appConfig);
    }

    return new Application(this.appConfig, this.crawler, this.printer);
  }

}
