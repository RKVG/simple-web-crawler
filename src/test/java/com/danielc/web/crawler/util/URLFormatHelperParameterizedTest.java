package com.danielc.web.crawler.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class URLFormatHelperParameterizedTest {

  private String url;
  private String expected;

  public URLFormatHelperParameterizedTest(String url, String expected) {
    this.url = url;
    this.expected = expected;
  }

  @Parameterized.Parameters(name = "{index}: shouldReturnCleanedUrl({0}) returns {1}")
  public static Collection<Object[]> testSetup() {
    return Arrays.asList(new Object[][]
      {
        // Invalid Inputs
        {null, null},
        {"", ""},
        {"  ", "  "},
        {"/", "/"},
        {"://", "://"},

        // Valid Inputs
        {"https://google.co.nz", "https://google.co.nz"},
        {"http://www.google.com", "http://www.google.com"},
        {"http://www.google.com/", "http://www.google.com"},
        {"http://www.google.com//", "http://www.google.com"},
        {"http://www.google.com/account", "http://www.google.com/account"},
        {"http://www.google.com/account/", "http://www.google.com/account"},
        {"http://www.google.com?query=test", "http://www.google.com"},
        {"http://www.google.com/?query=test", "http://www.google.com"},
        {"http://www.google.com#contact", "http://www.google.com"},
        {"http://www.google.com/#contact", "http://www.google.com"},
        {"http://www.google.com/find-us#contact?query=test", "http://www.google.com/find-us"},
        {"http://www.google.com/account/style.css", "http://www.google.com/account/style.css"},
        {"http://www.google.com/account/style.css?query=test", "http://www.google.com/account/style.css"}
      }
    );
  }

  @Test
  public void shouldReturnAcceptedDomains() {
    assertThat(URLFormatHelper.cleanUrl(url), is(expected));
  }

}
