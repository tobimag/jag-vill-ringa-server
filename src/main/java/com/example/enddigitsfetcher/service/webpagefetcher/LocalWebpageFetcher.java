package com.example.enddigitsfetcher.service.webpagefetcher;

import com.jasongoodwin.monads.Try;
import java.io.File;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("dev")
public class LocalWebpageFetcher implements WebpageFetcher {

  private final String filename;

  public LocalWebpageFetcher(WebpageFetcherProperties properties) {
    this.filename = properties.localFile;
  }

  @Override
  public Try<Document> get() {
    return Try.ofFailable(() -> Jsoup.parse(getFile(), "UTF-8"))
        .onFailure(throwable -> log.info(throwable.getMessage()));
  }

  private File getFile() {
    return new File(
        Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(filename)).getFile());
  }
}
