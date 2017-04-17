package com.danielc.web.crawler.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static com.danielc.web.crawler.config.PropertyConstants.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyConfigLoadingTest {

  @Mock
  private Properties mockedProperties;

  @InjectMocks
  private PropertyConfig candidate = PropertyConfig.getInstance("");

  @Test
  public void shouldLoadDefaultProperties() {
    // Arrange
    when(mockedProperties.getProperty(CRAWLER_REQUEST_TIMEOUT)).thenReturn("1000");
    when(mockedProperties.getProperty(CRAWLER_FOLLOW_REDIRECT)).thenReturn("true");
    when(mockedProperties.getProperty(CRAWLER_MAX_VISITED_URLS)).thenReturn("50");
    when(mockedProperties.getProperty(PRINTER_EXCLUDE_ERRORS)).thenReturn("false");
    when(mockedProperties.getProperty(PRINTER_REPORT_ERRORS)).thenReturn("true");

    // Act
    Optional<AppConfig> result = candidate.load();

    //Assert
    assertThat(result.isPresent(), is(true));

    result.ifPresent(props -> {
      assertThat(props, is(instanceOf(PropertyConfig.class)));
      assertThat(props.getCrawlerRequestTimeout(), is(1000));
      assertThat(props.isCrawlerFollowRedirects(), is(true));
      assertThat(props.getCrawlerMaxVisitedUrls(), is(50));
      assertThat(props.isPrinterExcludeErrors(), is(false));
      assertThat(props.isPrinterReportErrors(), is(true));
    });
  }

  @Test
  public void shouldReturnEmptyConfigOnNoInputStream() throws IOException {
    // Arrange
    PropertyConfig configSpy = Mockito.spy(candidate);

    when(configSpy.loadPropertyWithDefault()).thenReturn(null);

    // Act
    Optional<AppConfig> result = configSpy.load();

    // Assert
    assertThat(result.isPresent(), is(false));
  }

  @Test
  public void shouldReturnEmptyConfigOnException() throws IOException {
    // Arrange
    doThrow(new IOException("Big IOException")).when(mockedProperties).load(any(InputStream.class));

    // Act
    Optional<AppConfig> result = candidate.load();

    // Assert
    assertThat(result.isPresent(), is(false));
  }

  @Test
  public void shouldHandleInvalidRequestTimeoutWithValidDefault() {
    // Arrange
    when(mockedProperties.getProperty(CRAWLER_REQUEST_TIMEOUT)).thenReturn("one hundred");

    // Act
    Optional<AppConfig> result = candidate.load();

    //Assert
    assertThat(result.isPresent(), is(true));
    result.ifPresent(props -> assertThat(props.getCrawlerRequestTimeout(), is(5000)));
  }

  @Test
  public void shouldHandleInvalidMaxVisitedUrlsWithValidDefault() {
    // Arrange
    when(mockedProperties.getProperty(CRAWLER_MAX_VISITED_URLS)).thenReturn("ten");

    // Act
    Optional<AppConfig> result = candidate.load();

    //Assert
    assertThat(result.isPresent(), is(true));
    result.ifPresent(props -> assertThat(props.getCrawlerMaxVisitedUrls(), is(200)));
  }

}
