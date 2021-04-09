package com.example.enddigitsfetcher.domain.valueobject;

import static com.jasongoodwin.monads.Try.failure;
import static com.jasongoodwin.monads.Try.successful;

import com.jasongoodwin.monads.Try;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class EndDigits {

  String endDigits;

  public static Try<EndDigits> endDigits(String endDigits) {
    if (endDigits.length() == 2) {
      return successful(new EndDigits(endDigits));
    }
    return failure(new IllegalArgumentException("EndDigits has to be of length 2!"));
  }

}
