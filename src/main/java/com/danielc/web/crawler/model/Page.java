package com.danielc.web.crawler.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.util.Objects;
import java.util.Set;

public class Page implements Comparable<Page> {

  private String url;
  private Set<String> assets;
  private PageError error;

  Page(String url, Set<String> assets) {
    this.url = url;
    this.assets = assets;
  }

  Page(String url, PageError error) {
    this.url = url;
    this.error = error;
  }

  public String getUrl() {
    return url;
  }

  public Set<String> getAssets() {
    return assets;
  }

  public PageError getError() {
    return error;
  }

  public boolean isInError() {
    return error != null;
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

  @Override
  public int compareTo(Page other) {
    return ComparisonChain.start()
      .compare(this.url, other.getUrl(), Ordering.natural().nullsLast())
      .result();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("url", url)
      .add("assets", assets)
      .toString();
  }
}
