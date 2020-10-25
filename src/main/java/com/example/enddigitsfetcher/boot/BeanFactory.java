package com.example.enddigitsfetcher.boot;

import com.example.enddigitsfetcher.service.webpagefetcher.JsoupWebpageFetcher;
import com.example.enddigitsfetcher.service.webpagefetcher.LocalWebpageFetcher;
import com.example.enddigitsfetcher.service.webpagefetcher.WebpageFetcher;
import com.example.enddigitsfetcher.service.webpagefetcher.WebpageFetcherProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class BeanFactory {

  @Profile("!dev")
  @Bean
  WebpageFetcher jsoupWebpageFetcher(WebpageFetcherProperties properties) {
    return new JsoupWebpageFetcher(properties);
  }

  @Profile("dev")
  @Bean
  WebpageFetcher localWebpageFetcher(WebpageFetcherProperties properties) {
    return new LocalWebpageFetcher(properties);
  }

}
