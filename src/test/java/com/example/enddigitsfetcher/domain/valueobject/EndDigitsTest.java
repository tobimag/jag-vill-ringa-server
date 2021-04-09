package com.example.enddigitsfetcher.domain.valueobject;

import static com.example.enddigitsfetcher.domain.valueobject.EndDigits.endDigits;
import static com.example.enddigitsfetcher.domain.valueobject.matcher.EndDigitsWithValue.isEndDigitsWithValue;
import static com.example.enddigitsfetcher.matcher.FailedTryWithException.isFailedTryWithException;
import static com.example.enddigitsfetcher.matcher.SuccessfulTryWithValue.isSuccessfulTryWith;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class EndDigitsTest {

  @Test
  void shouldCreateEndDigits() {
    final String endDigits = "42";
    assertThat(endDigits(endDigits), isSuccessfulTryWith(isEndDigitsWithValue(endDigits)));
  }

  @Test
  void shouldFailCreatingEndDigits() {
    final String endDigits = "1337";
    assertThat(endDigits(endDigits), isFailedTryWithException(IllegalArgumentException.class));
  }
}
