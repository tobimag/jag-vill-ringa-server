package com.example.enddigitsfetcher.service.webpagefetcher;

import com.example.enddigitsfetcher.service.valueobjects.EndDigits;
import com.jasongoodwin.monads.Try;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class WebpageParser {

  public Try<EndDigits> parseWebpage(Document document) {
    return Try.ofFailable(() -> document
        .select("a")
        .stream()
        .map(Element::text)
        .filter(text -> text.contains("Slutsiffra just nu:"))
        .findFirst()
        .map(text -> text.substring(text.lastIndexOf(' ') + 1))
        .map(EndDigits::endDigits)
        .orElseThrow(NumberNotFoundException::new)
    );
  }

}
