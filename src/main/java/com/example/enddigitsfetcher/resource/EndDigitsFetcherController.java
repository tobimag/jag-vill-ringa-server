package com.example.enddigitsfetcher.resource;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

import com.example.enddigitsfetcher.service.enddigitsfetcher.EndDigitsFetcher;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class EndDigitsFetcherController {

  EndDigitsFetcher endDigitsFetcher;

  @GetMapping("/current-end-digits")
  public EndDigitsDto getCurrentEndDigits() {
    return endDigitsFetcher.fetchEndDigits()
        .onFailure(failure -> {
          throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Ending digits not found", failure);
        })
        .map(number -> new EndDigitsDto(number, OffsetDateTime.now()))
        .getUnchecked();
  }

  @GetMapping("latest-end-digits")
  public EndDigitsDto getLatestEndDigits() {
    return endDigitsFetcher.getLatestEndDigits()
        .onFailure(failure -> {
          throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Unable to retrieve end digits", failure);
        })
        .map(entity -> new EndDigitsDto(entity.getEndDigits(), entity.getTimestamp()))
        .getUnchecked();
  }

  @PostMapping("/store-current-end-digits")
  public EndDigitsDto storeCurrentEndDigits() {
    return endDigitsFetcher.storeEndDigits()
        .onFailure(failure -> {
          throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Failed to store current end digits", failure);
        })
        .map(entity -> new EndDigitsDto(entity.getEndDigits(), entity.getTimestamp()))
        .getUnchecked();
  }

}
