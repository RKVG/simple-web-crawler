package com.danielc.web.crawler.collector;

import java.util.Collection;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class NoWhitespaceCollector implements Collector {

  private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");

  @Override
  public Collection<String> collect(Collection<String> inputs) {
    if (isEmpty(inputs)) {
      return inputs;
    }

    return inputs.stream().filter(input -> !WHITESPACE_PATTERN.matcher(input).find()).collect(toList());
  }

}
