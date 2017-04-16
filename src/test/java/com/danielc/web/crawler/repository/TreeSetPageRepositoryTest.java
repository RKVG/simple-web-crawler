package com.danielc.web.crawler.repository;

import com.danielc.web.crawler.model.Page;
import com.danielc.web.crawler.model.PageBuilder;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;

public class TreeSetPageRepositoryTest {

  private TreeSetPageRepository candidate = new TreeSetPageRepository();

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

    assertThat(pages, contains(
      hasProperty("url", is("http://www.google.co.uk")),
      hasProperty("url", is("http://www.google.com"))
    ));
  }

}
