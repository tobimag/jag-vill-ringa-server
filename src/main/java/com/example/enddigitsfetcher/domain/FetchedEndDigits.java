package com.example.enddigitsfetcher.domain;

import com.example.enddigitsfetcher.domain.valueobject.EndDigits;
import com.jasongoodwin.monads.Try;
import java.time.OffsetDateTime;
import lombok.Value;

@Value
public class FetchedEndDigits {

  EndDigits endDigits;
  OffsetDateTime timestamp;

  public static Try<FetchedEndDigits> create(String endDigits, OffsetDateTime timestamp) {
    return EndDigits.endDigits(endDigits)
                    .map(ed -> new FetchedEndDigits(ed, timestamp));
  }

  public static FetchedEndDigits create(EndDigits endDigits, OffsetDateTime timestamp) {
    return new FetchedEndDigits(endDigits, timestamp);
  }
}
