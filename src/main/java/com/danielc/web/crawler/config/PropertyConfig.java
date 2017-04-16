package com.danielc.web.crawler.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static com.danielc.web.crawler.config.PropertyConstants.REQUEST_FOLLOW_REDIRECT;
import static com.danielc.web.crawler.config.PropertyConstants.REQUEST_TIMEOUT;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Mimic Spring @Configuration singleton bean
 */
public class PropertyConfig implements AppConfig {

  private Logger LOGGER = LoggerFactory.getLogger(PropertyConfig.class);

  private static final String DEFAULT_PROPERTY = "application.properties";
  private static PropertyConfig config;

  private String propertyFile = DEFAULT_PROPERTY;
  private Properties props = new Properties();

  private PropertyConfig(String fileName) {
    if (isNotBlank(fileName)) {
      this.propertyFile = fileName;

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

    try (InputStream inputStream = PropertyConfig.class.getClassLoader().getResourceAsStream(propertyFile)) {

      if (inputStream != null) {
        props.load(inputStream);

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

  @Override
  public int getRequestTimeout() {
    try {
      return Integer.parseInt(props.getProperty(REQUEST_TIMEOUT));

    } catch (NumberFormatException e) {
      LOGGER.warn("Cannot read value for {}! Using default value {}...", REQUEST_TIMEOUT, DEFAULT_REQUEST_TIMEOUT, e);
      return DEFAULT_REQUEST_TIMEOUT;
    }
  }

  @Override
  public boolean getFollowRedirects() {
    return Boolean.parseBoolean(props.getProperty(REQUEST_FOLLOW_REDIRECT));
  }

  // for test
  static void resetConfig() {
    config = null;
  }

}
