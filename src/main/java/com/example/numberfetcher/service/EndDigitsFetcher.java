package com.example.numberfetcher.service;

import com.jasongoodwin.monads.Try;
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

  public Try<String> fetchEndDigits() {
    return getWebpage()
        .map(this::parseWebpage)
        .onFailure(throwable -> log.info(throwable.getMessage()));
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
