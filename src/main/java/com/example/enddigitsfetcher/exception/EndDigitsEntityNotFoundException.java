package com.example.enddigitsfetcher.exception;

public class EndDigitsEntityNotFoundException extends RuntimeException {

  public EndDigitsEntityNotFoundException() {
    super("Unable to retrieve stored end digits!");
  }
}
