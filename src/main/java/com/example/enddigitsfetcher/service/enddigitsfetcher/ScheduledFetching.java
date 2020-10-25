package com.example.enddigitsfetcher.service.enddigitsfetcher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@AllArgsConstructor
public class ScheduledFetching {

  EndDigitsFetcher endDigitsFetcher;

  @Scheduled(cron = "*/5 07-10 * * SAT")
  public void fetch() {
    endDigitsFetcher.fetchEndDigits()
        .onSuccess(endDigits -> log.info("Fetched end-digits {}", endDigits))
        .onFailure(failure -> log.info("Failed fetching end-digits", failure));
  }

}
