package com.danielc.web.crawler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class URLFormatHelper {

  private static final Pattern FILE_PATTERN = Pattern.compile("^.+//.+/.+\\.(?<type>.[^./ ]+)$");
  private static final Pattern URL_PATTERN = Pattern.compile("^(?<baseUrl>.+//.[^#?/ ]+).*$");

  public static boolean isAssetUrl(String uri) {
    if (isBlank(uri)) {
      return false;
    }

    Matcher matcher = FILE_PATTERN.matcher(uri);
    return matcher.find();
  }

  public static String cleanUrl(String url) {
    if (isBlank(url)) {
      return url;
    }

    Matcher matcher = URL_PATTERN.matcher(url);
    return matcher.find() ? matcher.group("baseUrl") : url;
  }

}
