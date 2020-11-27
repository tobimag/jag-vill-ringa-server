package com.example.enddigitsfetcher.service.enddigitsfetcher;

import static com.jasongoodwin.monads.Try.ofFailable;

import com.example.enddigitsfetcher.repository.EndDigitsEntity;
import com.example.enddigitsfetcher.repository.EndDigitsRepository;
import com.example.enddigitsfetcher.service.webpagefetcher.WebpageFetcher;
import com.jasongoodwin.monads.Try;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EndDigitsFetcher {

  private final WebpageFetcher webpageFetcher;
  private final EndDigitsRepository repository;

  public Try<EndDigitsEntity> storeEndDigits() {
    return fetchEndDigits()
        .map(endDigit -> repository.save(EndDigitsEntity.create(endDigit)))
        .onFailure(throwable -> log.info(throwable.getMessage()));
  }

  public Try<String> fetchEndDigits() {
    return getWebpage()
        .map(this::parseWebpage)
        .onFailure(throwable -> log.info(throwable.getMessage()));
  }

  public Try<EndDigitsEntity> getLatestEndDigits() {
    return ofFailable(() -> repository.findTopByOrderByIdDesc()
        .orElseThrow(() -> new EntityNotFoundException("Unable to retrieve stored end digits!")));
  }

  private String parseWebpage(Document document) {
    return document
        .select("a")
        .stream()
        .map(Element::text)
        .filter(text -> text.contains("Slutsiffra just nu:"))
        .findFirst()
        .map(text -> text.substring(text.lastIndexOf(' ') + 1))
        .orElseThrow(NumberNotFoundException::new);
  }

  @SneakyThrows
  private Try<Document> getWebpage() {
    return webpageFetcher.get();
  }


}
