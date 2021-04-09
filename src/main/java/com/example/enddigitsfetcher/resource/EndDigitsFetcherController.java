package com.example.enddigitsfetcher.resource;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

import com.example.enddigitsfetcher.domain.FetchedEndDigits;
import com.example.enddigitsfetcher.service.enddigitsservice.EndDigitsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class EndDigitsFetcherController {

  private final EndDigitsService endDigitsFetcher;

  @GetMapping("/current-end-digits")
  public ResponseEntity<EndDigitsResponse> getCurrentEndDigits() {
    return endDigitsFetcher.fetchCurrentEndDigits()
                           .map(fetchedEndDigits -> ResponseEntity.ok(mapToResponse(fetchedEndDigits)))
                           .onFailure(failure -> {
                             throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Unable to parse end digits",
                                 failure);
                           })
                           .getUnchecked();
  }

  private EndDigitsResponse mapToResponse(FetchedEndDigits fetchedEndDigits) {
    return new EndDigitsResponse(fetchedEndDigits.getEndDigits().toString(), fetchedEndDigits.getTimestamp());
  }

  @GetMapping("/latest-end-digits")
  public ResponseEntity<EndDigitsResponse> getLatestEndDigits() {
    return endDigitsFetcher.getLatestEndDigits()
                           .map(fetchedEndDigits -> ResponseEntity.ok(mapToResponse(fetchedEndDigits)))
                           .onFailure(failure -> {
                             throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Unable to retrieve end digits",
                                 failure);
                           })
                           .getUnchecked();
  }

  @PostMapping("/store-current-end-digits")
  public ResponseEntity<EndDigitsResponse> storeCurrentEndDigits() {
    return endDigitsFetcher.storeCurrentEndDigits()
                           .map(fetchedEndDigits -> ResponseEntity.ok(mapToResponse(fetchedEndDigits)))
                           .onFailure(failure -> {
                             throw new ResponseStatusException(SERVICE_UNAVAILABLE,
                                 "Failed to store current end digits",
                                 failure);
                           })
                           .getUnchecked();
  }

}
