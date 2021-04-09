package com.example.enddigitsfetcher.repository;

import com.example.enddigitsfetcher.domain.FetchedEndDigits;
import com.jasongoodwin.monads.Try;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class EndDigitsMapper {

  EndDigitsEntity toEntity(FetchedEndDigits fetchedEndDigits) {
    return new EndDigitsEntity(fetchedEndDigits.getEndDigits().getValue(), fetchedEndDigits.getTimestamp());
  }

  Try<FetchedEndDigits> fromEntity(EndDigitsEntity entity) {
    return FetchedEndDigits.create(entity.getEndDigits(), entity.getTimestamp());
  }
}
