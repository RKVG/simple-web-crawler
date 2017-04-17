package com.danielc.web.crawler.collector;

import java.util.List;

public interface Collector {

  default boolean isEmpty(List<String> inputs) {
    return inputs == null || inputs.isEmpty();
  }

  List<String> collect(List<String> inputs);

}
