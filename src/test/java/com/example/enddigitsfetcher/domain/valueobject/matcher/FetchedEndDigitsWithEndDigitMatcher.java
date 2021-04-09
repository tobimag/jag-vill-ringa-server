package com.example.enddigitsfetcher.domain.valueobject.matcher;

import com.example.enddigitsfetcher.domain.FetchedEndDigits;
import com.example.enddigitsfetcher.domain.valueobject.EndDigits;
import lombok.AllArgsConstructor;
import org.mockito.ArgumentMatcher;

@AllArgsConstructor
public class FetchedEndDigitsWithEndDigitMatcher implements ArgumentMatcher<FetchedEndDigits> {

  private final EndDigits left;

  public static FetchedEndDigitsWithEndDigitMatcher equalsFetchedEndDigits(EndDigits endDigits) {
    return new FetchedEndDigitsWithEndDigitMatcher(endDigits);
  }

  @Override
  public boolean matches(FetchedEndDigits fetchedEndDigits) {
    return left.equals(fetchedEndDigits.getEndDigits());
  }
}
