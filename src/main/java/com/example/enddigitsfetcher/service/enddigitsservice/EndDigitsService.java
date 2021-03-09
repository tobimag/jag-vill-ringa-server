package com.example.enddigitsfetcher.service.enddigitsservice;

import static com.jasongoodwin.monads.Try.ofFailable;

import com.example.enddigitsfetcher.repository.EndDigitsEntity;
import com.example.enddigitsfetcher.repository.EndDigitsRepository;
import com.example.enddigitsfetcher.service.webpagefetcher.WebpageFetcher;
import com.example.enddigitsfetcher.service.webpagefetcher.WebpageParser;
import com.jasongoodwin.monads.Try;
import javax.persistence.EntityNotFoundException;
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

  public Try<Void> storeEndDigits() {
    return fetchCurrentEndDigits()
        .onSuccess(repository::save)
        .onFailure(throwable -> log.info(throwable.getMessage()))
        .map(entity -> null);
  }

  public Try<EndDigitsEntity> fetchCurrentEndDigits() {
    return webpageFetcher.get()
        .flatMap(webpageParser::parseWebpage)
        .onFailure(throwable -> log.info(throwable.getMessage()))
        .map(EndDigitsEntity::create);
  }

  public Try<EndDigitsEntity> getLatestEndDigits() {
    return ofFailable(() -> repository.findTopByOrderByIdDesc().get())
        .recoverWith(t -> Try.failure(new EntityNotFoundException("Unable to retrieve stored end digits!")));
  }

}
