package com.example.enddigitsfetcher.exception;

public class EndDigitsParsingException extends RuntimeException {

  public EndDigitsParsingException() {
    super("Unable to parse end digits!");
  }
}
