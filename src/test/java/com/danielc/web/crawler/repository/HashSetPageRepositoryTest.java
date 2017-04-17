package com.danielc.web.crawler.repository;

import com.danielc.web.crawler.model.Page;
import com.danielc.web.crawler.model.PageBuilder;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HashSetPageRepositoryTest {

  private HashSetPageRepository candidate = new HashSetPageRepository();

  @Test
  public void shouldStorePage() {
    candidate.store(
      PageBuilder.newInstance()
        .url("http://www.google.com")
        .assets(Sets.newHashSet("pic.png"))
        .build()
    );

    candidate.store(
      PageBuilder.newInstance()
        .url("http://www.google.com")
        .assets(Sets.newHashSet("pic.jpeg"))
        .build()
    );

    candidate.store(
      PageBuilder.newInstance()
        .url("http://www.google.co.uk")
        .assets(Sets.newHashSet("pic.jpeg"))
        .build()
    );

    Set<Page> pages = candidate.findAll();

    assertThat(pages, containsInAnyOrder(
      hasProperty("url", is("http://www.google.co.uk")),
      hasProperty("url", is("http://www.google.com"))
    ));
  }

  @Test
  public void shouldFindPage() {
    candidate.store(
      PageBuilder.newInstance()
        .url("http://www.google.com")
        .assets(Sets.newHashSet("pic.png"))
        .build()
    );

    candidate.store(
      PageBuilder.newInstance()
        .url("http://www.google.co.uk")
        .assets(Sets.newHashSet("pic.jpeg"))
        .build()
    );

    candidate.store(
      PageBuilder.newInstance()
        .url("http://www.google.co.nz")
        .error(403, "forbidden")
        .build()
    );

    assertThat(candidate.countAll(), is(3));
    assertThat(candidate.findAll(), containsInAnyOrder(
      hasProperty("url", is("http://www.google.com")),
      hasProperty("url", is("http://www.google.co.uk")),
      hasProperty("url", is("http://www.google.co.nz"))
    ));

    assertThat(candidate.countInError(), is(1));
    assertThat(candidate.findAllInError(), contains(
      hasProperty("url", is("http://www.google.co.nz"))
    ));

    assertThat(candidate.findAllWithoutError(), containsInAnyOrder(
      hasProperty("url", is("http://www.google.com")),
      hasProperty("url", is("http://www.google.co.uk"))
    ));
  }

}
