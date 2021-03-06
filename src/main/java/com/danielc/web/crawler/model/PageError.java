package com.danielc.web.crawler.model;

public class PageError {

  private int httpStatus;
  private String message;

  PageError(int httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  public int getHttpStatus() {
    return httpStatus;
  }

  public String getMessage() {
    return message;
  }

}
