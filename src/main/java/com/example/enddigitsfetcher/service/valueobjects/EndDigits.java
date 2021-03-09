package com.example.enddigitsfetcher.service.valueobjects;

import lombok.Value;

@Value
public class EndDigits {

  String endDigits;

  public static EndDigits endDigits(String endDigits) {
    if (endDigits.length() == 2) {
      return new EndDigits(endDigits);
    }
    throw new IllegalArgumentException("EndDigits has to be of length 2!");
  }

  private EndDigits(String endDigits) {
    this.endDigits = endDigits;
  }
}
