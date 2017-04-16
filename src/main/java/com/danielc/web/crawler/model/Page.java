package com.danielc.web.crawler.model;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import static com.danielc.web.crawler.util.URLFormatHelper.cleanUrl;

public class Page {

  private String url;
  private Set<String> assets;

  private Page(String url, Set<String> assets) {
    this.url = cleanUrl(url);
    this.assets = assets;
  }

  public static PageBuilder builder() {
    return new PageBuilder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Page page = (Page) o;
    return Objects.equals(url, page.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url);
  }

  public static class PageBuilder {

    private String url;
    private Set<String> assets;

    public PageBuilder url(String url) {
      this.url = url;
      return this;
    }

    public PageBuilder assets(Collection<String> assets) {
      if (this.assets == null) {
        this.assets = Sets.newTreeSet();
      }

      this.assets.addAll(assets);
      return this;
    }

    public Page build() {
      return new Page(this.url, this.assets);
    }

  }

}
