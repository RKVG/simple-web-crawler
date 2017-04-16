package com.danielc.web.crawler.app;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.repository.PageRepository;
import com.danielc.web.crawler.repository.UrlRepository;
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

  @Mock
  private UrlRepository mockUrlRepository;

  @Mock
  private PageRepository mockPageRepository;

  @Test(expected = IllegalStateException.class)
  public void shouldThrowExceptionOnNoAppConfig() {
    ApplicationBuilder.newInstance().build();
  }

  @Test(expected = IllegalStateException.class)
  public void shouldThrowExceptionOnNoUrlRepository() {
    ApplicationBuilder.newInstance()
      .appConfig(mockAppConfig)
      .pageRepository(mockPageRepository)
      .crawler(mockCrawler)
      .printer(mockPrinter)
      .build();
  }

  @Test(expected = IllegalStateException.class)
  public void shouldThrowExceptionOnNoPageRepository() {
    ApplicationBuilder.newInstance()
      .appConfig(mockAppConfig)
      .urlRepository(mockUrlRepository)
      .crawler(mockCrawler)
      .printer(mockPrinter)
      .build();
  }

  @Test
  public void shouldBuildExpectedApplication() {
    Application candidate = ApplicationBuilder.newInstance()
      .appConfig(mockAppConfig)
      .urlRepository(mockUrlRepository)
      .pageRepository(mockPageRepository)
      .crawler(mockCrawler)
      .printer(mockPrinter)
      .build();

    assertThat(candidate, is(notNullValue()));
    assertThat(candidate.config, is(mockAppConfig));
    assertThat(candidate.crawler, is(mockCrawler));
    assertThat(candidate.printer, is(mockPrinter));
  }

}
