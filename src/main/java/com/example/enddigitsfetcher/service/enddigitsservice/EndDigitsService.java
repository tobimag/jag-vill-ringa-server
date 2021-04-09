package com.example.enddigitsfetcher.service.enddigitsservice;

import static java.time.OffsetDateTime.now;

import com.example.enddigitsfetcher.domain.FetchedEndDigits;
import com.example.enddigitsfetcher.domain.valueobject.EndDigits;
import com.example.enddigitsfetcher.repository.EndDigitsRepository;
import com.example.enddigitsfetcher.service.webpagefetcher.WebpageFetcher;
import com.example.enddigitsfetcher.service.webpagefetcher.WebpageParser;
import com.jasongoodwin.monads.Try;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EndDigitsService {

  private final WebpageFetcher webpageFetcher;
  private final WebpageParser webpageParser;
  private final EndDigitsRepository repository;

  public Try<FetchedEndDigits> storeCurrentEndDigits() {
    return this.fetchCurrentEndDigits()
               .flatMap(repository::save)
               .onFailure(throwable -> log.info(throwable.getMessage()));
  }

  public Try<FetchedEndDigits> storeEndDigits(EndDigits endDigits) {
    return repository.save(FetchedEndDigits.create(endDigits, OffsetDateTime.now()))
                     .onFailure(throwable -> log.info(throwable.getMessage()));
  }

  public Try<FetchedEndDigits> fetchCurrentEndDigits() {
    return webpageFetcher.getWebpage()
                         .flatMap(webpageParser::parseWebpage)
                         .map(endDigits -> new FetchedEndDigits(endDigits, now()))
                         .onFailure(throwable -> log.info(throwable.getMessage()));
  }

  public Try<FetchedEndDigits> getLatestEndDigits() {
    return repository.getLatestEndDigits()
                     .onFailure(throwable -> log.info(throwable.getMessage()));
  }
}
