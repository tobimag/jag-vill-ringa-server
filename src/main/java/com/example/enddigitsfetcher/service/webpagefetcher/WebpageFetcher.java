package com.example.enddigitsfetcher.service.webpagefetcher;

import com.jasongoodwin.monads.Try;
import org.jsoup.nodes.Document;

public interface WebpageFetcher {

  Try<Document> get();

}
