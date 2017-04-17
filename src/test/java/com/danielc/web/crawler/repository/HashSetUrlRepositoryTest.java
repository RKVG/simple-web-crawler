package com.danielc.web.crawler.repository;

import com.google.common.collect.Lists;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class HashSetUrlRepositoryTest {

  private HashSetUrlRepository candidate = new HashSetUrlRepository();

  @Test
  public void shouldStoreVisitedUrls() {
    candidate.storeVisitedUrl("http://www.google.com");
    candidate.storeVisitedUrl("http://www.google.co.uk");

    assertThat(candidate.isUrlVisited("http://www.google.com"), is(true));
    assertThat(candidate.isUrlVisited("http://www.google.co.uk"), is(true));
    assertThat(candidate.isUrlVisited("http://www.google.co.nz"), is(false));
  }

  @Test
  public void shouldStoreUnvisitedUrls() {
    candidate.storeUnvisitedUrl("http://www.google.com");
    candidate.storeUnvisitedUrl("http://www.google.co.uk");

    assertThat(candidate.countUnvisitedUrls(), is(2));
    assertThat(candidate.findAllUnvisitedUrls(), containsInAnyOrder(
      "http://www.google.co.uk",
      "http://www.google.com"
    ));

    candidate.refreshUnvisitedUrls(Lists.newArrayList("http://www.google.co.nz"));

    assertThat(candidate.countUnvisitedUrls(), is(1));
    assertThat(candidate.findAllUnvisitedUrls(), contains(
      "http://www.google.co.nz"
    ));
  }

}
