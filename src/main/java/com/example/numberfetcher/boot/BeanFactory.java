package com.example.numberfetcher.boot;

import com.example.numberfetcher.service.JsoupWebpageFetcher;
import com.example.numberfetcher.service.LocalWebpageFetcher;
import com.example.numberfetcher.service.WebpageFetcher;
import com.example.numberfetcher.service.WebpageFetcherProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class BeanFactory {

  @Profile("live")
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
