package com.example.enddigitsfetcher.service.webpagefetcher;

public class NumberNotFoundException extends RuntimeException {

  public NumberNotFoundException() {
    super("Number not found while parsing web page!");
  }

}
