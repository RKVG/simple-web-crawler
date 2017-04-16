package com.danielc.web.crawler.model;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PageTest {

  private static final String TEST_URL = "http://www.google.com";
  private static final Set<String> TEST_ASSETS = Sets.newHashSet("pic.png");

  @Test
  public void shouldImplementComparable() {
    Page candidate1 = PageBuilder.newInstance()
      .url("http://www.a.com")
      .build();

    Page candidate2 = PageBuilder.newInstance()
      .url("http://www.b.com")
      .build();

    Page candidate3 = PageBuilder.newInstance()
      .build();

    assertThat(candidate1.compareTo(candidate2), is(-1));
    assertThat(candidate2.compareTo(candidate3), is(-1));
    assertThat(candidate3.compareTo(candidate1), is(1));
    assertThat(candidate1.compareTo(candidate1), is(0));
  }

  @Test
  public void shouldImplementToString() {
    Page candidate = PageBuilder.newInstance()
      .url(TEST_URL)
      .assets(TEST_ASSETS)
      .build();

    assertThat(candidate.toString(), containsString("url=" + TEST_URL));
    assertThat(candidate.toString(), containsString("assets=" + TEST_ASSETS));
  }

  @Test
  public void shouldImplementEqualsAndHashCode() {
    Page candidate1 = PageBuilder.newInstance()
      .url(TEST_URL)
      .assets(TEST_ASSETS)
      .build();

    Page candidate2 = PageBuilder.newInstance()
      .url("http://www.google.co.uk")
      .assets(TEST_ASSETS)
      .build();

    Page candidate3 = PageBuilder.newInstance()
      .url("http://www.google.co.uk/")
      .assets(Sets.newHashSet())
      .build();

    assertThat(candidate1.equals(candidate1), is(true));
    assertThat(candidate1.equals(null), is(false));
    assertThat(candidate1.equals(new Object()), is(false));
    assertThat(candidate1.equals(candidate2), is(false));
    assertThat(candidate2.equals(candidate3), is(true));

    assertTrue(candidate1.hashCode() != candidate2.hashCode());
    assertTrue(candidate2.hashCode() == candidate3.hashCode());
  }

}
