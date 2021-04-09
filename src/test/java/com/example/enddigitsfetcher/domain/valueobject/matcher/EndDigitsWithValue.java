package com.example.enddigitsfetcher.domain.valueobject.matcher;

import static org.hamcrest.core.Is.is;

import com.example.enddigitsfetcher.domain.valueobject.EndDigits;
import lombok.AllArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

@AllArgsConstructor
public class EndDigitsWithValue extends TypeSafeDiagnosingMatcher<EndDigits> {

  private final Matcher<String> matcher;

  public static Matcher<EndDigits> isEndDigitsWithValue(String value) {
    return new EndDigitsWithValue(is(value));
  }

  @Override
  protected boolean matchesSafely(EndDigits item, Description mismatchDescription) {
    if (matcher.matches(item.getEndDigits())) {
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
