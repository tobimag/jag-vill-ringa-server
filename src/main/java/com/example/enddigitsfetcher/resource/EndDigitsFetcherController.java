package com.example.enddigitsfetcher.resource;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

import com.example.enddigitsfetcher.domain.FetchedEndDigits;
import com.example.enddigitsfetcher.domain.valueobject.EndDigits;
import com.example.enddigitsfetcher.service.enddigitsservice.EndDigitsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/end-digits")
public class EndDigitsFetcherController {

  private final EndDigitsService endDigitsFetcher;

  @GetMapping("current")
  public ResponseEntity<EndDigitsResponse> getCurrentEndDigits() {
    return endDigitsFetcher.fetchCurrentEndDigits()
                           .map(this::createResponse)
                           .onFailure(this::handleFailure)
                           .getUnchecked();
  }

  @GetMapping("latest")
  public ResponseEntity<EndDigitsResponse> getLatestEndDigits() {
    return endDigitsFetcher.getLatestEndDigits()
                           .map(this::createResponse)
                           .onFailure(this::handleFailure)
                           .getUnchecked();
  }

  @PostMapping("current")
  public ResponseEntity<EndDigitsResponse> storeCurrentEndDigits() {
    return endDigitsFetcher.storeCurrentEndDigits()
                           .map(this::createResponse)
                           .onFailure(this::handleFailure)
                           .getUnchecked();
  }

  @PostMapping("{end-digits}")
  public ResponseEntity<EndDigitsResponse> storeEndDigits(@PathVariable("end-digits") String endDigits) {
    return EndDigits.endDigits(endDigits)
                    .flatMap(endDigitsFetcher::storeEndDigits)
                    .map(this::createResponse)
                    .onFailure(this::handleFailure)
                    .getUnchecked();
  }

  private ResponseEntity<EndDigitsResponse> createResponse(FetchedEndDigits fetchedEndDigits) {
    return ResponseEntity.ok(new EndDigitsResponse(fetchedEndDigits.getEndDigits().getValue(), fetchedEndDigits.getTimestamp()));
  }

  private void handleFailure(Throwable failure) {
    throw new ResponseStatusException(SERVICE_UNAVAILABLE, failure.getMessage(), failure);
  }


}
