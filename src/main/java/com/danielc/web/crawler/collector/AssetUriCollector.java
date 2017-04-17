package com.danielc.web.crawler.collector;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class AssetUriCollector implements Collector {

  private static final List<String> RECOGNISED_LINKS = Lists.newArrayList("HTML");
  private static final Pattern FILE_PATTERN = Pattern.compile("^.+://.[^://]+/.+\\.(?<type>.[^./ ]{0,6})$");

  @Override
  public List<String> collect(List<String> inputs) {
    if (isEmpty(inputs)) {
      return inputs;
    }

    return inputs.stream().filter(AssetUriCollector::isAssetUri).collect(toList());
  }

  private static boolean isAssetUri(String uri) {
    if (isBlank(uri)) {
      return false;
    }

    Matcher matcher = FILE_PATTERN.matcher(uri);
    return matcher.find() && !RECOGNISED_LINKS.contains(matcher.group("type").toUpperCase());
  }

}
