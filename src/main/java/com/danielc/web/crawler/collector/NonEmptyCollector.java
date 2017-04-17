package com.danielc.web.crawler.collector;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class NonEmptyCollector implements Collector {

  @Override
  public List<String> collect(List<String> inputs) {
    if (isEmpty(inputs)) {
      return inputs;
    }

    return inputs.stream().filter(StringUtils::isNotBlank).collect(toList());
  }

}
