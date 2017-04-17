package com.danielc.web.crawler.util;

import org.apache.commons.lang3.CharEncoding;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class URLFormatHelper {

  private static final Pattern URL_PATTERN = Pattern.compile("^(?<baseUrl>.+://.*[/.*]*[^/]).*$");

  public static String cleanUrl(String url) {
    if (isBlank(url)) {
      return url;
    }

    String result = tryUTF8Decoding(url);
    result = stripAllAfter(result, "?");
    result = stripAllAfter(result, "#");
    return stripForwardSlash(result);
  }

  private static String stripAllAfter(String url, String character) {
    if (url.contains(character)) {
      return url.substring(0, url.indexOf(character));
    }
    return url;
  }

  private static String stripForwardSlash(String url) {
    Matcher matcher = URL_PATTERN.matcher(url);
    return matcher.find() ? matcher.group("baseUrl") : url;
  }

  private static String tryUTF8Decoding(String url) {
    try {
      return URLDecoder.decode(url, CharEncoding.UTF_8);
    } catch (UnsupportedEncodingException e) {
      return url;
    }
  }

}
