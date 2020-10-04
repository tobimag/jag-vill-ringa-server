package com.example.enddigitsfetcher.service.webpagefetcher;

import static lombok.AccessLevel.PUBLIC;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "end-digits-fetcher")
@ConstructorBinding
@FieldDefaults(level = PUBLIC, makeFinal = true)
@AllArgsConstructor
public class WebpageFetcherProperties {

  String url;
  String localFile;

}
