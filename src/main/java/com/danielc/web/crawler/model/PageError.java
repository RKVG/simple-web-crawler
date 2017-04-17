package com.danielc.web.crawler.model;

public class PageError {

  private int httpStatus;
  private String message;

  public PageError(int httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

}
