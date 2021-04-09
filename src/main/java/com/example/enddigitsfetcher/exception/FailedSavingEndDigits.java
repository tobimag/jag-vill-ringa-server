package com.example.enddigitsfetcher.exception;

public class FailedSavingEndDigits extends RuntimeException {

  public FailedSavingEndDigits(Throwable cause) {
    super("Unable to save end digits!", cause);
  }
}
