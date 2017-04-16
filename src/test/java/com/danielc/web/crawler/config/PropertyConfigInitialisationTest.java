package com.danielc.web.crawler.config;

import org.junit.Test;

import static com.danielc.web.crawler.config.PropertyConfig.getInstance;
import static com.danielc.web.crawler.config.PropertyConfig.resetConfig;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PropertyConfigInitialisationTest {

  @Test
  public void shouldHandleEmptyInput() {
    resetConfig();
    assertThat(getInstance(null).load().isPresent(), is(true));

    resetConfig();
    assertThat(getInstance("").load().isPresent(), is(true));

    resetConfig();
    assertThat(getInstance("  ").load().isPresent(), is(true));
  }

  @Test
  public void shouldReturnEmptyConfigOnInvalidInput() {
    resetConfig();
    assertThat(getInstance("invalid.properties").load().isPresent(), is(false));
  }

}
