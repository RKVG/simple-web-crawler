package com.danielc.web.crawler.app;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.service.Crawler;
import com.danielc.web.crawler.service.Printer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationBuilderTest {

  @Mock
  private AppConfig mockAppConfig;

  @Mock
  private Crawler mockCrawler;

  @Mock
  private Printer mockPrinter;

  @Test(expected = IllegalStateException.class)
  public void shouldThrowExceptionOnNoAppConfig() {
    ApplicationBuilder.newInstance().build();
  }

  @Test
  public void shouldBuildExpectedApplication() {
    Application result = ApplicationBuilder.newInstance()
      .appConfig(mockAppConfig)
      .crawler(mockCrawler)
      .printer(mockPrinter)
      .build();

    assertThat(result, is(notNullValue()));
    assertThat(result.config, is(mockAppConfig));
    assertThat(result.crawler, is(mockCrawler));
    assertThat(result.printer, is(mockPrinter));
  }

}
