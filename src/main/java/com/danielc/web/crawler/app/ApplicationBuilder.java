package com.danielc.web.crawler.app;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.repository.PageRepository;
import com.danielc.web.crawler.repository.UrlRepository;
import com.danielc.web.crawler.service.Crawler;
import com.danielc.web.crawler.service.Printer;

public class ApplicationBuilder {

  private AppConfig appConfig;
  private Crawler crawler;
  private Printer printer;
  private UrlRepository urlRepository;
  private PageRepository pageRepository;

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

  public ApplicationBuilder urlRepository(UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
    return this;
  }

  public ApplicationBuilder pageRepository(PageRepository pageRepository) {
    this.pageRepository = pageRepository;
    return this;
  }

  public Application build() {
    if (this.appConfig == null) {
      throw new IllegalStateException("No application config found!");
    }

    if (this.urlRepository == null || this.pageRepository == null) {
      throw new IllegalStateException("Error when setting repository!");
    }

    if (this.crawler != null) {
      this.crawler.setRepositories(urlRepository, pageRepository);
    }

    if (this.printer != null) {
      this.printer.setRepository(pageRepository);
    }

    return new Application(this.appConfig, this.crawler, this.printer);
  }

}
