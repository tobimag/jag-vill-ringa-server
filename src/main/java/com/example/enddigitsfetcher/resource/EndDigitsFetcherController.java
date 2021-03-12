package com.example.enddigitsfetcher.resource;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

import com.example.enddigitsfetcher.service.enddigitsservice.EndDigitsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class EndDigitsFetcherController {

  private final EndDigitsService endDigitsFetcher;

  @GetMapping("/current-end-digits")
  public EndDigitsResponse getCurrentEndDigits() {
    return endDigitsFetcher.fetchCurrentEndDigits()
        .onFailure(failure -> {
          throw new ResponseStatusException(SERVICE_UNAVAILABLE, "End digits not found", failure);
        })
        .map(entity -> new EndDigitsResponse(entity.getEndDigits(), entity.getTimestamp()))
        .getUnchecked();
  }

  @GetMapping("/latest-end-digits")
  public EndDigitsResponse getLatestEndDigits() {
    return endDigitsFetcher.getLatestEndDigits()
        .onFailure(failure -> {
          throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Unable to retrieve end digits", failure);
        })
        .map(entity -> new EndDigitsResponse(entity.getEndDigits(), entity.getTimestamp()))
        .getUnchecked();
  }

  @PostMapping("/store-current-end-digits")
  public void storeCurrentEndDigits() {
    endDigitsFetcher.storeEndDigits()
        .onFailure(failure -> {
          throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Failed to store current end digits", failure);
        })
        .getUnchecked();
  }

}
