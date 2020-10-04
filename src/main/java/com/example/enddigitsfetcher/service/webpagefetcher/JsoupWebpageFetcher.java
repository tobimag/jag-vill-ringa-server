package com.example.enddigitsfetcher.service.webpagefetcher;

import com.jasongoodwin.monads.Try;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
public class JsoupWebpageFetcher implements WebpageFetcher {

  private final String url;

  public JsoupWebpageFetcher(WebpageFetcherProperties properties) {
    this.url = properties.url;
  }

  @Override
  public Try<Document> get() {
    return Try.ofFailable(() -> Jsoup.connect(url).get())
        .onFailure(throwable -> log.info(throwable.getMessage()));
  }

}
