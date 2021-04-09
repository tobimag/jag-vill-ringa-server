package com.example.enddigitsfetcher.matcher;

import com.jasongoodwin.monads.Try;
import lombok.AllArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

@AllArgsConstructor
public class SuccessfulTryWithValue<T> extends TypeSafeDiagnosingMatcher<Try<? extends T>> {

  private final Matcher<T> matcher;

  public static <T> Matcher<Try<? extends T>> isSuccessfulTryWith(final Matcher<T> matcher) {
    return new SuccessfulTryWithValue<>(matcher);
  }

  @Override
  protected boolean matchesSafely(final Try<? extends T> item, final Description mismatchDescription) {
    if (item.isSuccess()) {
      if (matcher.matches(item.getUnchecked())) {
        return true;
      } else {
        mismatchDescription.appendText("was a Try whose value");
        matcher.describeMismatch(item.getUnchecked(), mismatchDescription);
        return false;
      }
    } else {
      mismatchDescription.appendText("was not a success");
      return false;
    }
  }

  @Override
  public void describeTo(Description description) {
    description
        .appendText("a Try with a value that ")
        .appendDescriptionOf(matcher);
  }
}