package com.danielc.web.crawler.collector;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class NoWhitespaceCollector implements Collector {

  private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");

  @Override
  public List<String> collect(List<String> inputs) {
    if (isEmpty(inputs)) {
      return inputs;
    }

    return inputs.stream().filter(input -> !WHITESPACE_PATTERN.matcher(input).find()).collect(toList());
  }

}
