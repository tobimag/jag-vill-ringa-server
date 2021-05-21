package com.example.enddigitsfetcher.service.webpagefetcher;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "end-digits-fetcher")
@ConstructorBinding
@Value
public class WebpageFetcherProperties {

  String url;
  String localFile;
}
