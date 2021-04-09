package com.example.enddigitsfetcher.service.webpagefetcher;

import com.example.enddigitsfetcher.domain.valueobject.EndDigits;
import com.example.enddigitsfetcher.exception.EndDigitsParsingException;
import com.jasongoodwin.monads.Try;
import java.util.Optional;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class WebpageParser {

  public Try<EndDigits> parseWebpage(Document document) {
    return Try.ofFailable(() -> findEndDigits(document).orElseThrow(EndDigitsParsingException::new))
              .flatMap(EndDigits::endDigits);
  }

  private Optional<String> findEndDigits(Document document) {
    return document.select("a")
                   .stream()
                   .map(Element::text)
                   .filter(text -> text.contains("Slutsiffra just nu:"))
                   .findFirst()
                   .map(text -> text.substring(text.lastIndexOf(' ') + 1));
  }

}
