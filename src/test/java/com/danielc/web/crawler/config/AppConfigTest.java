package com.danielc.web.crawler.config;

import org.junit.Test;

import java.util.Optional;

import static com.danielc.web.crawler.config.AppConfig.DEFAULT_REQUEST_FOLLOW_REDIRECT;
import static com.danielc.web.crawler.config.AppConfig.DEFAULT_REQUEST_TIMEOUT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppConfigTest {

  private AppConfig candidate = Optional::empty;

  @Test
  public void shouldDefaultRequestTimeoutValue() {
    assertThat(candidate.getRequestTimeout(), is(DEFAULT_REQUEST_TIMEOUT));
  }

  @Test
  public void shouldDefaultFollowRedirectsValue() {
    assertThat(candidate.getFollowRedirects(), is(DEFAULT_REQUEST_FOLLOW_REDIRECT));
  }

}
