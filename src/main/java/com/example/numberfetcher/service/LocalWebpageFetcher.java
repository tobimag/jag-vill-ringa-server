package com.example.numberfetcher.service;

import com.jasongoodwin.monads.Try;
import java.io.File;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
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
