package com.danielc.web.crawler.app;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.repository.PageRepository;
import com.danielc.web.crawler.repository.UrlRepository;
import com.danielc.web.crawler.service.Crawler;
import com.danielc.web.crawler.service.Printer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

  private static final String TEST_URL = "http://www.google.com";

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

  private Application candidate;

  @Before
  public void init() {
    candidate = ApplicationBuilder.newInstance()
      .appConfig(mockAppConfig)
      .urlRepository(mockUrlRepository)
      .pageRepository(mockPageRepository)
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

    System.setIn(new ByteArrayInputStream(TEST_URL.getBytes()));

    // Act
    candidate.run();

    // Assert
    verify(mockCrawler).setConfig(eq(mockAppConfig));
    verify(mockCrawler).crawl(eq(TEST_URL));
    verify(mockPrinter).print();
  }

}
