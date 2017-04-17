package com.danielc.web.crawler.util;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DomainMatcherParameterizedTest {

  private String url;
  private List<String> expected;

  public DomainMatcherParameterizedTest(String url, List<String> expected) {
    this.url = url;
    this.expected = expected;
  }

  @Parameters(name = "{index}: shouldReturnAcceptedDomainList({0}) returns {1}")
  public static Collection<Object[]> testSetup() {
    return Arrays.asList(new Object[][]
      {
        // Invalid Inputs
        {null, Lists.newArrayList()},
        {"", Lists.newArrayList()},
        {"  ", Lists.newArrayList()},
        {"/", Lists.newArrayList()},
        {"://", Lists.newArrayList()},

        // Valid Inputs
        {"ftp://google.co.nz", Lists.newArrayList("http://google.co.nz", "https://google.co.nz")},
        {"http://google.co.nz", Lists.newArrayList("http://google.co.nz", "https://google.co.nz")},
        {"https://google.co.nz", Lists.newArrayList("http://google.co.nz", "https://google.co.nz")},
        {"http://www.google.com", Lists.newArrayList("http://www.google.com", "https://www.google.com")},
        {"http://www.google.com/", Lists.newArrayList("http://www.google.com", "https://www.google.com")},
        {"http://www.google.com//", Lists.newArrayList("http://www.google.com", "https://www.google.com")},
        {"http://www.google.com/account", Lists.newArrayList("http://www.google.com", "https://www.google.com")},
        {"http://www.google.com/account/", Lists.newArrayList("http://www.google.com", "https://www.google.com")},
        {"http://www.google.com?query=test", Lists.newArrayList("http://www.google.com", "https://www.google.com")},
        {"http://www.google.com/?query=test", Lists.newArrayList("http://www.google.com", "https://www.google.com")},
        {"http://www.google.com#contact", Lists.newArrayList("http://www.google.com", "https://www.google.com")},
        {"http://www.google.com/#contact", Lists.newArrayList("http://www.google.com", "https://www.google.com")},
        {"http://www.google.com#contact?query=test", Lists.newArrayList("http://www.google.com", "https://www.google.com")},
      }
    );
  }

  @Test
  public void shouldReturnAcceptedDomains() {
    DomainMatcher matcher = new DomainMatcher(url);
    assertThat(matcher.getAcceptedDomains(), is(expected));
  }

}
