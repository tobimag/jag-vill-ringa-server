package com.example.enddigitsfetcher.matchers;

import com.jasongoodwin.monads.Try;
import lombok.AllArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.Is;

@AllArgsConstructor
public class IsFailedTryWithException extends TypeSafeDiagnosingMatcher<Try<?>> {

  private final Matcher<?> expectedException;

  public static Matcher<Try<?>> isFailedTryWithException(Class<? extends Throwable> e) {
    return new IsFailedTryWithException(Is.isA(e));
  }

  @Override
  protected boolean matchesSafely(Try<?> item, Description mismatchDescription) {
    if (!item.isSuccess()) {
      try {
        item.get();
        return false;
      } catch (Throwable t) {
        if (expectedException.matches(t)) {
          return true;
        } else {
          mismatchDescription.appendText("was a failed Try whose exception");
          expectedException.describeMismatch(t, mismatchDescription);
          return false;
        }
      }
    } else {
      mismatchDescription.appendText("was not a failure");
      return false;
    }
  }


  @Override
  public void describeTo(Description description) {
    description
        .appendText("a failed Try with an exception that")
        .appendDescriptionOf(expectedException);
  }

}