package com.example.enddigitsfetcher.service.webpagefetcher;

import com.jasongoodwin.monads.Try;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("!dev")
public class JsoupWebpageFetcher implements WebpageFetcher {

  private final String url;

  public JsoupWebpageFetcher(WebpageFetcherProperties properties) {
    this.url = properties.url;
  }

  @Override
  public Try<Document> getWebpage() {
    return Try.ofFailable(() -> Jsoup.connect(url).get());
  }

}
