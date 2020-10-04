package com.example.enddigitsfetcher.service.enddigitsfetcher;

public class NumberNotFoundException extends RuntimeException {

  NumberNotFoundException() {
    super("Number not found while parsing web page!");
  }

}
