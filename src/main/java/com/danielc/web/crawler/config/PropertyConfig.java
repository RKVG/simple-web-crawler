package com.danielc.web.crawler.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
  public int getRequestTimeout() {
    try {
      LOGGER.debug(props.getProperty(REQUEST_TIMEOUT));
      return Integer.parseInt(props.getProperty(REQUEST_TIMEOUT));

    } catch (NumberFormatException e) {
      LOGGER.warn("Cannot read value for {}! Using default value {}...", REQUEST_TIMEOUT, DEFAULT_REQUEST_TIMEOUT, e);
      return DEFAULT_REQUEST_TIMEOUT;
    }
  }

  @Override
  public boolean getFollowRedirects() {
    LOGGER.debug(props.getProperty(REQUEST_FOLLOW_REDIRECT));
    return Boolean.parseBoolean(props.getProperty(REQUEST_FOLLOW_REDIRECT));
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
