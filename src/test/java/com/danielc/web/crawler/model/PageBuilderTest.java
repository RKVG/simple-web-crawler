package com.danielc.web.crawler.model;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PageBuilderTest {

  private static final String TEST_URL = "http://www.google.com";
  private static final Set<String> TEST_ASSETS = Sets.newHashSet("pic.png");

  @Test
  public void shouldBuildExpectedApplication() {
    Page candidate = PageBuilder.newInstance()
      .url(TEST_URL)
      .assets(TEST_ASSETS)
      .build();

    assertThat(candidate, is(notNullValue()));
    assertThat(candidate.getUrl(), is(TEST_URL));
    assertThat(candidate.getAssets(), is(TEST_ASSETS));
  }

  @Test
  public void shouldAllowAddingMultipleAssetSets() {
    Page candidate = PageBuilder.newInstance()
      .url(TEST_URL)
      .assets(TEST_ASSETS)
      .assets(Sets.newHashSet("pic.jpeg"))
      .build();

    assertThat(candidate, is(notNullValue()));
    assertThat(candidate.getUrl(), is(TEST_URL));
    assertThat(candidate.getAssets(), hasItems("pic.png", "pic.jpeg"));
  }

}
