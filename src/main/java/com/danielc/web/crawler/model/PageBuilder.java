package com.danielc.web.crawler.model;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;

import static com.danielc.web.crawler.util.URLFormatHelper.cleanUrl;

public class PageBuilder {

  private String url;
  private Set<String> assets;

  public static PageBuilder newInstance() {
    return new PageBuilder();
  }

  public PageBuilder url(String url) {
    this.url = cleanUrl(url);
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
