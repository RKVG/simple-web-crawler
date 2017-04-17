package com.danielc.web.crawler.collector;

import java.util.Collection;

public interface Collector {

  default boolean isEmpty(Collection<String> inputs) {
    return inputs == null || inputs.isEmpty();
  }

  Collection<String> collect(Collection<String> inputs);

}
