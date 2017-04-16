package com.danielc.web.crawler.config;

import java.util.Optional;

public interface AppConfig {

  int DEFAULT_REQUEST_TIMEOUT = 1000 * 5;
  boolean DEFAULT_REQUEST_FOLLOW_REDIRECT = false;

  Optional<AppConfig> load();

  default int getRequestTimeout() {
    return DEFAULT_REQUEST_TIMEOUT;
  }

  default boolean getFollowRedirects() {
    return DEFAULT_REQUEST_FOLLOW_REDIRECT;
  }

}
