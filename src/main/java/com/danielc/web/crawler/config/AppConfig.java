package com.danielc.web.crawler.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class AppConfig {

  private Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);
  private Properties prop = new Properties();

  private static final String DEFAULT_PROPERTY = "application.properties";

  private static final String REQUEST_TIMEOUT = "app.request.timeout";
  private static final String REQUEST_FOLLOW_REDIRECT = "app.follow-redirect";
  private static final int DEFAULT_REQUEST_TIMEOUT = 1000 * 5;

  private static AppConfig config;

  private AppConfig() {
  }

  public static AppConfig getInstance() {
    if (config == null) {
      config = new AppConfig();
    }
    return config;
  }

  public Optional<AppConfig> load(String configFile) {
    String propertyFile = DEFAULT_PROPERTY;

    if (isNotBlank(configFile)) {
      propertyFile = configFile;

    } else {
      LOGGER.debug("No config file supplied. Loading default {}...", DEFAULT_PROPERTY);
    }

    try (InputStream inputStream = AppConfig.class.getClassLoader().getResourceAsStream(propertyFile)) {

      if (inputStream != null) {
        prop.load(inputStream);

      } else {
        LOGGER.error("Cannot find property file {}. Please check your file path.", propertyFile);
        return Optional.empty();
      }

    } catch (IOException e) {
      LOGGER.error("Error when loading property file {}!", propertyFile, e);
      return Optional.empty();
    }

    return Optional.of(this);
  }

  public int getRequestTimeout() {
    try {
      return Integer.parseInt(prop.getProperty(REQUEST_TIMEOUT));

    } catch (NumberFormatException e) {
      LOGGER.error("Cannot read value for {}! Using default value {}...", REQUEST_TIMEOUT, DEFAULT_REQUEST_TIMEOUT, e);
      return DEFAULT_REQUEST_TIMEOUT;
    }
  }

  public boolean getFollowRedirects() {
    return Boolean.parseBoolean(prop.getProperty(REQUEST_FOLLOW_REDIRECT));
  }

}
