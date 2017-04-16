package com.danielc.web.crawler.app;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.model.Page;
import com.danielc.web.crawler.model.PageBuilder;
import com.danielc.web.crawler.service.Crawler;
import com.danielc.web.crawler.service.Printer;
import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

  private static final String TEST_URL = "http://www.google.com";
  private static final Set<Page> TEST_PAGES = Sets.newHashSet(PageBuilder.newInstance().url(TEST_URL).build());

  @Mock
  private AppConfig mockAppConfig;

  @Mock
  private Crawler mockCrawler;

  @Mock
  private Printer mockPrinter;

  private Application candidate;

  @Before
  public void init() {
    candidate = ApplicationBuilder.newInstance()
      .appConfig(mockAppConfig)
      .crawler(mockCrawler)
      .printer(mockPrinter)
      .build();
  }

  @After
  public void reset() {
    System.setIn(System.in);
  }

  @Test
  public void shouldCrawlingAndPrintResults() {
    // Arrange
    when(mockAppConfig.load()).thenReturn(Optional.of(mockAppConfig));
    when(mockCrawler.crawl(anyString())).thenReturn(TEST_PAGES);

    System.setIn(new ByteArrayInputStream(TEST_URL.getBytes()));

    // Act
    candidate.run();

    // Assert
    verify(mockCrawler).setConfig(eq(mockAppConfig));
    verify(mockCrawler).crawl(eq(TEST_URL));
    verify(mockPrinter).print(TEST_PAGES);
  }

}
