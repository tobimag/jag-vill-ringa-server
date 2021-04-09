package com.example.enddigitsfetcher.repository;

import com.example.enddigitsfetcher.domain.FetchedEndDigits;
import com.example.enddigitsfetcher.exception.EndDigitsEntityNotFoundException;
import com.example.enddigitsfetcher.exception.FailedSavingEndDigits;
import com.jasongoodwin.monads.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EndDigitsRepository {

  private final EndDigitsDao dao;

  public Try<FetchedEndDigits> save(FetchedEndDigits fetchedEndDigits) {
    return Try.ofFailable(() -> dao.save(EndDigitsMapper.toEntity(fetchedEndDigits)))
              .onFailure(FailedSavingEndDigits::new)
              .flatMap(entity -> FetchedEndDigits.create(entity.getEndDigits(), entity.getTimestamp()));
  }

  public Try<FetchedEndDigits> getLatestEndDigits() {
    return Try.ofFailable(() -> dao.findTopByOrderByIdDesc().orElseThrow(EndDigitsEntityNotFoundException::new))
              .flatMap(EndDigitsMapper::fromEntity);
  }
}
