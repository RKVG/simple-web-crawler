package com.danielc.web.crawler.config;

import java.util.Optional;

public interface AppConfig {

  int DEFAULT_CRAWLER_REQUEST_TIMEOUT = 1000 * 5;
  int DEFAULT_CRAWLER_MAX_VISITED_URLS = 200;
  boolean DEFAULT_CRAWLER_FOLLOW_REDIRECT = false;
  boolean DEFAULT_PRINTER_EXCLUDE_ERRORS = true;
  boolean DEFAULT_PRINTER_REPORT_ERRORS = true;

  Optional<AppConfig> load();

  default int getCrawlerRequestTimeout() {
    return DEFAULT_CRAWLER_REQUEST_TIMEOUT;
  }

  default int getCrawlerMaxVisitedUrls() {
    return DEFAULT_CRAWLER_MAX_VISITED_URLS;
  }

  default boolean isCrawlerFollowRedirects() {
    return DEFAULT_CRAWLER_FOLLOW_REDIRECT;
  }

  default boolean isPrinterExcludeErrors() {
    return DEFAULT_PRINTER_EXCLUDE_ERRORS;
  }

  default boolean isPrinterReportErrors() {
    return DEFAULT_PRINTER_REPORT_ERRORS;
  }

}
