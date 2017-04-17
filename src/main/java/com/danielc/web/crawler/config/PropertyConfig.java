package com.danielc.web.crawler.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static com.danielc.web.crawler.config.PropertyConstants.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Mimic Spring @Configuration singleton bean
 */
public class PropertyConfig implements AppConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(PropertyConfig.class);
  private static final String DEFAULT_PROPERTY = "application.properties";
  private static PropertyConfig config;

  private String customProperty;
  private Properties props = new Properties();

  private PropertyConfig(String fileName) {
    if (isNotBlank(fileName)) {
      this.customProperty = fileName;

    } else {
      LOGGER.debug("No config file supplied. Use default {}", DEFAULT_PROPERTY);
    }
  }

  public static PropertyConfig getInstance(String fileName) {
    if (config == null) {
      config = new PropertyConfig(fileName);
    }
    return config;
  }

  @Override
  public Optional<AppConfig> load() {

    try (InputStream inputStream = loadPropertyWithDefault()) {

      if (inputStream != null) {
        props.load(inputStream);

      } else {
        LOGGER.error("Cannot find property file {}. Please check your file path.", getLoadingProperty());
        return Optional.empty();
      }

    } catch (IOException e) {
      LOGGER.error("Error when loading property file {}!", getLoadingProperty(), e);
      return Optional.empty();
    }

    return Optional.of(this);
  }

  @Override
  public int getCrawlerRequestTimeout() {
    return parseInt(CRAWLER_REQUEST_TIMEOUT, DEFAULT_CRAWLER_REQUEST_TIMEOUT);
  }

  @Override
  public int getCrawlerMaxVisitedUrls() {
    return parseInt(CRAWLER_MAX_VISITED_URLS, DEFAULT_CRAWLER_MAX_VISITED_URLS);
  }

  @Override
  public boolean isCrawlerFollowRedirects() {
    return Boolean.parseBoolean(props.getProperty(CRAWLER_FOLLOW_REDIRECT));
  }

  @Override
  public boolean isPrinterExcludeErrors() {
    return Boolean.parseBoolean(props.getProperty(PRINTER_EXCLUDE_ERRORS));
  }

  @Override
  public boolean isPrinterReportErrors() {
    return Boolean.parseBoolean(props.getProperty(PRINTER_REPORT_ERRORS));
  }

  private int parseInt(String propertyName, int defaultValue) {
    try {
      return Integer.parseInt(props.getProperty(propertyName));

    } catch (NumberFormatException e) {
      LOGGER.warn("Cannot read value for {}! Using default value {}...", propertyName, defaultValue, e);
      return defaultValue;
    }
  }

  private InputStream loadPropertyWithDefault() throws FileNotFoundException {
    if (isNotBlank(customProperty)) {
      return new FileInputStream(customProperty);
    }
    return PropertyConfig.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTY);
  }

  private String getLoadingProperty() {
    return isNotBlank(customProperty) ? customProperty : DEFAULT_PROPERTY;
  }

  // for test
  static void resetConfig() {
    config = null;
  }

}
