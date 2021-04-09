package com.example.enddigitsfetcher.domain.valueobject.matcher;

import static org.hamcrest.core.Is.is;

import com.example.enddigitsfetcher.domain.FetchedEndDigits;
import lombok.AllArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

@AllArgsConstructor
public class FetchedEndDigitsWithEndDigits extends TypeSafeDiagnosingMatcher<FetchedEndDigits> {

  private final Matcher<String> matcher;

  public static Matcher<FetchedEndDigits> isFetchedEndDigitsWithValue(String value) {
    return new FetchedEndDigitsWithEndDigits(is(value));
  }

  @Override
  protected boolean matchesSafely(FetchedEndDigits item, Description mismatchDescription) {
    if (matcher.matches(item.getEndDigits().getValue())) {
      return true;
    } else {
      mismatchDescription.appendText("was an EndDigits whose value");
      matcher.describeMismatch(item.getEndDigits(), mismatchDescription);
      return false;
    }
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("an EndDigits with a value ").appendDescriptionOf(matcher);
  }
}
