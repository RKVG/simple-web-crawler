package com.danielc.web.crawler.config;

import org.junit.Test;

import java.util.Optional;

import static com.danielc.web.crawler.config.AppConfig.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppConfigTest {

  private AppConfig candidate = Optional::empty;

  @Test
  public void shouldDefaultMockUserAgent() {
    assertThat(candidate.getCrawlerMockUserAgent(), is(DEFAULT_CRAWLER_MOCK_USER_AGENT));
  }

  @Test
  public void shouldDefaultRequestTimeoutValue() {
    assertThat(candidate.getCrawlerRequestTimeout(), is(DEFAULT_CRAWLER_REQUEST_TIMEOUT));
  }

  @Test
  public void shouldDefaultMaxVisitedUrlsValue() {
    assertThat(candidate.getCrawlerMaxVisitedUrls(), is(DEFAULT_CRAWLER_MAX_VISITED_URLS));
  }

  @Test
  public void shouldDefaultFollowRedirectsValue() {
    assertThat(candidate.isCrawlerFollowRedirects(), is(DEFAULT_CRAWLER_FOLLOW_REDIRECT));
  }

  @Test
  public void shouldDefaultExcludeErrorsValue() {
    assertThat(candidate.isPrinterExcludeErrors(), is(DEFAULT_PRINTER_EXCLUDE_ERRORS));
  }

  @Test
  public void shouldDefaultReportErrorsValue() {
    assertThat(candidate.isPrinterReportErrors(), is(DEFAULT_PRINTER_REPORT_ERRORS));
  }

}
