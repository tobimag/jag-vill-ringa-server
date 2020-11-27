package com.example.enddigitsfetcher.service.enddigitsfetcher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class ScheduledFetching {

  EndDigitsFetcher endDigitsFetcher;

  @Scheduled(cron = "0 */5 07-09 * * SAT", zone = "CET")
  public void fetch() {
    endDigitsFetcher.fetchEndDigits()
        .onSuccess(endDigits -> log.info("Fetched end-digits {}", endDigits))
        .onFailure(failure -> log.info("Failed fetching end-digits", failure));
  }

}
