package com.example.enddigitsfetcher.repository;

import com.example.enddigitsfetcher.domain.FetchedEndDigits;
import com.jasongoodwin.monads.Try;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EndDigitsMapper {

  EndDigitsEntity toEntity(FetchedEndDigits fetchedEndDigits) {
    return new EndDigitsEntity(fetchedEndDigits.getEndDigits().toString(), fetchedEndDigits.getTimestamp());
  }

  Try<FetchedEndDigits> fromEntity(EndDigitsEntity entity) {
    return FetchedEndDigits.create(entity.getEndDigits(), entity.getTimestamp());
  }
}
