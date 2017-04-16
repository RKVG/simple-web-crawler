package com.danielc.web.crawler.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static com.danielc.web.crawler.config.PropertyConstants.REQUEST_FOLLOW_REDIRECT;
import static com.danielc.web.crawler.config.PropertyConstants.REQUEST_TIMEOUT;
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
    when(mockedProperties.getProperty(REQUEST_TIMEOUT)).thenReturn("1000");
    when(mockedProperties.getProperty(REQUEST_FOLLOW_REDIRECT)).thenReturn("true");

    // Act
    Optional<AppConfig> result = candidate.load();

    //Assert
    assertThat(result.isPresent(), is(true));

    result.ifPresent(props -> {
      assertThat(props, is(instanceOf(PropertyConfig.class)));
      assertThat(props.getRequestTimeout(), is(1000));
      assertThat(props.getFollowRedirects(), is(true));
    });
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
  public void shouldHandleInvalidRequestTimeoutValidWithDefault() {
    // Arrange
    when(mockedProperties.getProperty(REQUEST_TIMEOUT)).thenReturn("one hundred");

    // Act
    Optional<AppConfig> result = candidate.load();

    //Assert
    assertThat(result.isPresent(), is(true));
    result.ifPresent(props -> assertThat(props.getRequestTimeout(), is(5000)));
  }

}
