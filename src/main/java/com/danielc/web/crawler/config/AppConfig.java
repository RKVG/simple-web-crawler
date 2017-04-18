package com.danielc.web.crawler.config;

import java.util.Optional;

public interface AppConfig {

  String DEFAULT_CRAWLER_MOCK_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
  int DEFAULT_CRAWLER_REQUEST_TIMEOUT = 1000 * 5;
  int DEFAULT_CRAWLER_MAX_VISITED_URLS = 200;
  boolean DEFAULT_CRAWLER_FOLLOW_REDIRECT = false;
  boolean DEFAULT_PRINTER_EXCLUDE_ERRORS = true;
  boolean DEFAULT_PRINTER_REPORT_ERRORS = true;

  Optional<AppConfig> load();

  default String getCrawlerMockUserAgent() {
    return DEFAULT_CRAWLER_MOCK_USER_AGENT;
  }

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
