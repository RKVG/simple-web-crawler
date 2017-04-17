package com.danielc.web.crawler.collector;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class NonEmptyCollector implements Collector {

  @Override
  public Collection<String> collect(Collection<String> inputs) {
    if (isEmpty(inputs)) {
      return inputs;
    }

    return inputs.stream().filter(StringUtils::isNotBlank).collect(toList());
  }

}
