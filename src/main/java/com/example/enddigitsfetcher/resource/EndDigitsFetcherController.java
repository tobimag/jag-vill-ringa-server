package com.example.enddigitsfetcher.resource;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

import com.example.enddigitsfetcher.service.enddigitsfetcher.EndDigitsFetcher;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class EndDigitsFetcherController {

  EndDigitsFetcher endDigitsFetcher;

  @GetMapping("/get-new-end-digits")
  public EndDigitsDto getNewEndDigits() {
    return endDigitsFetcher.fetchEndDigits()
        .onFailure(failure -> {
          throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Ending digits not found", failure);
        })
        .map(number -> new EndDigitsDto(number, OffsetDateTime.now()))
        .getUnchecked();
  }

}
