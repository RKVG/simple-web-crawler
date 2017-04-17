package com.danielc.web.crawler.service;

import com.danielc.web.crawler.config.AppConfig;
import com.danielc.web.crawler.model.Page;
import com.danielc.web.crawler.repository.PageRepository;
import com.danielc.web.crawler.repository.UrlRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.danielc.web.crawler.service.JsoupCrawler.ACCEPTED_CONTENT_TYPE;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.jsoup.Connection.Response;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Jsoup.class)
public class JsoupCrawlerTest {

  @Mock
  private AppConfig mockAppConfig;

  @Mock
  private UrlRepository mockUrlRepository;

  @Mock
  private PageRepository mockPageRepository;

  @Mock
  private Connection mockConnection;

  @Mock
  private Response mockResponse;

  @Mock
  private Document mockDocument;

  @Mock
  private Elements mockElements;

  private JsoupCrawler candidate = new JsoupCrawler();

  @Before
  public void setup() throws Exception {
    mockStatic(Jsoup.class);

    when(Jsoup.connect(anyString())).thenReturn(mockConnection);
    when(mockConnection.timeout(anyInt())).thenReturn(mockConnection);
    when(mockConnection.followRedirects(anyBoolean())).thenReturn(mockConnection);
    when(mockConnection.ignoreContentType(anyBoolean())).thenReturn(mockConnection);
    when(mockConnection.execute()).thenReturn(mockResponse);

    when(mockResponse.parse()).thenReturn(mockDocument);
    when(mockDocument.select(anyString())).thenReturn(mockElements);

    when(mockUrlRepository.countUnvisitedUrls()).thenReturn(1).thenReturn(0);
    when(mockUrlRepository.findAllUnvisitedUrls()).thenReturn(Lists.newArrayList("http://www.google.com")).thenReturn(Lists.newArrayList());

    candidate.setConfig(mockAppConfig);
    candidate.setRepositories(mockUrlRepository, mockPageRepository);
  }

  @Test
  public void shouldUseCorrectConnectionConfiguration() {
    // Arrange
    final int testRequestTimeout = 1234;
    final boolean testFollowRedirects = true;

    when(mockAppConfig.getCrawlerRequestTimeout()).thenReturn(testRequestTimeout);
    when(mockAppConfig.isCrawlerFollowRedirects()).thenReturn(testFollowRedirects);

    when(mockResponse.statusCode()).thenReturn(200);
    when(mockResponse.contentType()).thenReturn(ACCEPTED_CONTENT_TYPE);

    // Act
    candidate.crawl("http://www.google.com");

    // Assert
    verify(mockConnection).timeout(eq(testRequestTimeout));
    verify(mockConnection).followRedirects(eq(testFollowRedirects));
    verify(mockConnection).ignoreContentType(eq(true));
  }

  @Test
  public void shouldStoreUrlOnError() {
    // Arrange
    final String testCrawlUrl = "http://www.google.co.uk";

    when(mockUrlRepository.findAllUnvisitedUrls()).thenReturn(Lists.newArrayList(testCrawlUrl)).thenReturn(Lists.newArrayList());

    when(mockResponse.statusCode()).thenReturn(200);
    when(mockResponse.contentType()).thenReturn(null);

    // Act
    candidate.crawl(testCrawlUrl);

    // Assert
    verify(mockUrlRepository).storeVisitedUrl(eq(testCrawlUrl));
  }

  @Test
  public void shouldErrorOnEmptyContentType() {
    // Arrange
    when(mockResponse.statusCode()).thenReturn(200);
    when(mockResponse.contentType()).thenReturn(null);

    // Act
    candidate.crawl("http://www.google.com");

    // Assert
    ArgumentCaptor<Page> storedPage = ArgumentCaptor.forClass(Page.class);
    verify(mockPageRepository).store(storedPage.capture());

    Page errorPage = storedPage.getValue();
    assertThat(errorPage.getUrl(), is("http://www.google.com"));
    assertThat(errorPage.isInError(), is(true));
  }

  @Test
  public void shouldErrorOnUnacceptedContentType() {
    // Arrange
    when(mockResponse.statusCode()).thenReturn(200);
    when(mockResponse.contentType()).thenReturn("unknown");

    // Act
    candidate.crawl("http://www.google.com");

    // Assert
    ArgumentCaptor<Page> storedPage = ArgumentCaptor.forClass(Page.class);
    verify(mockPageRepository).store(storedPage.capture());

    Page errorPage = storedPage.getValue();
    assertThat(errorPage.getUrl(), is("http://www.google.com"));
    assertThat(errorPage.getError(), is(notNullValue()));
    assertThat(errorPage.getError().getHttpStatus(), is(500));
    assertThat(errorPage.getError().getMessage(), containsString("Unsupported mime type unknown"));
    assertThat(errorPage.isInError(), is(true));
  }

  @Test
  public void shouldErrorOnHttpStatusException() {
    // Arrange
    when(mockResponse.contentType()).thenReturn(ACCEPTED_CONTENT_TYPE);
    when(mockResponse.statusCode()).thenReturn(301);
    when(mockResponse.statusMessage()).thenReturn("Moved Permanently");

    // Act
    candidate.crawl("http://www.google.com");

    // Assert
    ArgumentCaptor<Page> storedPage = ArgumentCaptor.forClass(Page.class);
    verify(mockPageRepository).store(storedPage.capture());

    Page errorPage = storedPage.getValue();
    assertThat(errorPage.getUrl(), is("http://www.google.com"));
    assertThat(errorPage.getError(), is(notNullValue()));
    assertThat(errorPage.getError().getHttpStatus(), is(301));
    assertThat(errorPage.getError().getMessage(), containsString("Moved Permanently"));
    assertThat(errorPage.isInError(), is(true));
  }

  @Test
  public void shouldExtractPageInfoOnSuccess() throws Exception {
    // Arrange
    final String testCrawlUrl = "http://www.google.com";

    Element mockLinkElement = mock(Element.class);
    Element mockHtmlElement = mock(Element.class);
    Element mockAssetElement = mock(Element.class);
    Elements mockElements = new Elements(Lists.newArrayList(mockLinkElement, mockHtmlElement, mockAssetElement));

    when(mockLinkElement.attr(anyString())).thenReturn("http://www.google.com");
    when(mockHtmlElement.attr(anyString())).thenReturn("http://www.google.com/old-page.html");
    when(mockAssetElement.attr(anyString())).thenReturn("http://www.google.com/read-me.pdf");

    when(mockAppConfig.getCrawlerMaxVisitedUrls()).thenReturn(2);
    when(mockResponse.statusCode()).thenReturn(200);
    when(mockResponse.contentType()).thenReturn(ACCEPTED_CONTENT_TYPE);
    when(mockDocument.select(anyString())).thenReturn(mockElements);
    when(mockUrlRepository.isUrlVisited(eq(testCrawlUrl))).thenReturn(true);

    // Act
    candidate.crawl(testCrawlUrl);

    // Assert
    ArgumentCaptor<Page> storedPage = ArgumentCaptor.forClass(Page.class);
    verify(mockPageRepository).store(storedPage.capture());

    Page page = storedPage.getValue();
    assertThat(page.getUrl(), is(testCrawlUrl));
    assertThat(page.isInError(), is(false));
    assertThat(page.getAssets(), is(not(empty())));
    assertThat(page.getAssets(), contains("http://www.google.com/read-me.pdf"));

    verify(mockUrlRepository).storeVisitedUrl("http://www.google.com");
    verify(mockUrlRepository).refreshUnvisitedUrls(Sets.newHashSet("http://www.google.com/old-page.html"));
  }

}
