package com.danielc.web.crawler.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DomainMatcherMatchingTest {

  private DomainMatcher candidate = new DomainMatcher("http://www.google.com/");

  @Test
  public void shouldMatchUrlCorrectly() {
    assertThat(candidate.matchedDomain(null), is(false));
    assertThat(candidate.matchedDomain(""), is(false));
    assertThat(candidate.matchedDomain("  "), is(false));
    assertThat(candidate.matchedDomain("www.google.com"), is(false));
    assertThat(candidate.matchedDomain("ftp://www.google.com"), is(false));

    assertThat(candidate.matchedDomain("http://www.google.com"), is(true));
    assertThat(candidate.matchedDomain("https://www.google.com"), is(true));
  }

}
