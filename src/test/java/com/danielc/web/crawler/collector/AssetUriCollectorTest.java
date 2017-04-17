package com.danielc.web.crawler.collector;

import com.google.common.collect.Lists;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AssetUriCollectorTest {

  private AssetUriCollector candidate = new AssetUriCollector();

  @Test
  public void shouldHandleInvalidInputs() {
    assertThat(candidate.collect(null), is(nullValue()));
    assertThat(candidate.collect(Lists.newArrayList()), is(Lists.newArrayList()));
  }

  @Test
  public void shouldCollectStringsThatMatchesAssetUriPattern() {
    assertThat(candidate.collect(
      Lists.newArrayList(
        null,
        "",
        "  ",
        "/",
        "/.png",
        "/pic.png",
        "http://test.png",
        "http://test.com",
        "http://test.com/.png",
        "http://test.com/pic.png.",
        "http://test.com?continue=http://test2.com",
        "https://consent.google.com/status?continue=https://www.google.co.uk&pc=s&timestamp=1492378051",
        "http://test.com/pic.png",
        "https://test.com/pic.png",
        "http://test.co.uk/pic.png",
        "http://test.co.uk/pic.me.png",
        "http://test.co.uk/pic.me.png.png",
        "http://test.com?continue=http://test2.com/pic.png"
      )),

      is(Lists.newArrayList(
        "http://test.com/pic.png",
        "https://test.com/pic.png",
        "http://test.co.uk/pic.png",
        "http://test.co.uk/pic.me.png",
        "http://test.co.uk/pic.me.png.png",
        "http://test.com?continue=http://test2.com/pic.png"
      ))
    );
  }

}
