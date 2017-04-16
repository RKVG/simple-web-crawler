package com.danielc.web.crawler.util;

import org.junit.Test;

import static com.danielc.web.crawler.util.URLFormatHelper.cleanUrl;
import static com.danielc.web.crawler.util.URLFormatHelper.isAssetUrl;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class URLFormatHelperTest {

  @Test
  public void shouldRecogniseInvalidAssetUrl() {
    assertThat(isAssetUrl(null), is(false));
    assertThat(isAssetUrl(""), is(false));
    assertThat(isAssetUrl("  "), is(false));
    assertThat(isAssetUrl("/"), is(false));
    assertThat(isAssetUrl("/.png"), is(false));
    assertThat(isAssetUrl("/pic.png"), is(false));
    assertThat(isAssetUrl("http://test.png"), is(false));
    assertThat(isAssetUrl("http://test.com/.png"), is(false));
    assertThat(isAssetUrl("http://test.com/pic.png."), is(false));
  }

  @Test
  public void shouldRecogniseValidAssetUrl() {
    assertThat(isAssetUrl("C://test.com/pic.png"), is(true));
    assertThat(isAssetUrl("ftp://test.com/pic.png"), is(true));
    assertThat(isAssetUrl("http://test.com/pic.png"), is(true));
    assertThat(isAssetUrl("https://test.com/pic.png"), is(true));
    assertThat(isAssetUrl("http://test.co.uk/pic.png"), is(true));
    assertThat(isAssetUrl("http://test.co.uk/pic.me.png"), is(true));
    assertThat(isAssetUrl("http://test.co.uk/pic.me.png.png"), is(true));
  }

  @Test
  public void shouldReturnCleanedUrl() {
    assertThat(cleanUrl(null), is(nullValue()));
    assertThat(cleanUrl(""), is(""));
    assertThat(cleanUrl("  "), is("  "));
    assertThat(cleanUrl("/"), is("/"));

    assertThat(cleanUrl("http://www.google.com"), is("http://www.google.com"));
    assertThat(cleanUrl("http://www.google.com/"), is("http://www.google.com"));
    assertThat(cleanUrl("http://www.google.com//"), is("http://www.google.com"));
    assertThat(cleanUrl("http://www.google.com?query=test"), is("http://www.google.com"));
    assertThat(cleanUrl("http://www.google.com#contact"), is("http://www.google.com"));
    assertThat(cleanUrl("http://www.google.com#contact?query=test"), is("http://www.google.com"));
  }


}
