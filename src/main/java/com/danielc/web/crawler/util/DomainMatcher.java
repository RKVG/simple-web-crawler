package com.danielc.web.crawler.util;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class DomainMatcher {

  private static final Pattern DOMAIN_PATTERN = Pattern.compile("^(?<domain>.+://.[^/?#]+).*$");
  private static final String PROTOCOL_ENDS = "://";

  private List<String> acceptedProtocols = Lists.newArrayList("http", "https");
  private List<String> acceptedDomains = Lists.newArrayList();

  public DomainMatcher(String url) {
    getDomain(url)
      .ifPresent(domain ->
        acceptedProtocols.forEach(protocol ->
          acceptedDomains.add(protocol + PROTOCOL_ENDS + domain)
        )
      );
  }

  public boolean matchedDomain(String url) {
    if (isBlank(url)) {
      return false;
    }

    for (String acceptedDomain : acceptedDomains) {
      if (url.startsWith(acceptedDomain)) {
        return true;
      }
    }
    return false;
  }

  List<String> getAcceptedDomains() {
    return acceptedDomains;
  }

  private static Optional<String> getDomain(String url) {
    if (isBlank(url)) {
      return Optional.empty();
    }

    Matcher matcher = DOMAIN_PATTERN.matcher(url);
    return matcher.find() ? Optional.of(stripAllBefore(matcher.group("domain"), PROTOCOL_ENDS)) : Optional.empty();
  }

  private static String stripAllBefore(String url, String protocol) {
    if (url.contains(protocol)) {
      return url.substring(url.indexOf(protocol) + protocol.length(), url.length());
    }
    return url;
  }

}
