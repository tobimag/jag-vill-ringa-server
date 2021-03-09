package com.example.enddigitsfetcher.service.enddigitsservice;

import static com.example.enddigitsfetcher.matchers.IsFailedTryWithException.isFailedTryWithException;
import static com.example.enddigitsfetcher.matchers.IsSuccessfulTryWithValue.isSuccessfulTryWithValue;
import static com.example.enddigitsfetcher.service.valueobjects.EndDigits.endDigits;
import static com.jasongoodwin.monads.Try.failure;
import static com.jasongoodwin.monads.Try.successful;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

import com.example.enddigitsfetcher.repository.EndDigitsEntity;
import com.example.enddigitsfetcher.repository.EndDigitsRepository;
import com.example.enddigitsfetcher.service.valueobjects.EndDigits;
import com.example.enddigitsfetcher.service.webpagefetcher.NumberNotFoundException;
import com.example.enddigitsfetcher.service.webpagefetcher.WebpageFetcher;
import com.example.enddigitsfetcher.service.webpagefetcher.WebpageParser;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EndDigitsFetcherTest {

  @Mock
  WebpageFetcher webpageFetcher;

  @Mock
  WebpageParser webpageParser;

  @Mock
  EndDigitsRepository repository;

  @InjectMocks
  EndDigitsService subject;

  @Test
  void shouldGetCurrentEndDigits() {
    final String expectedEndDigit = "14";
    when(webpageFetcher.get()).thenReturn(successful(Jsoup.parse(String.format("<HTML><a>Slutsiffra just nu: %s</a></HTML>", expectedEndDigit))));
    when(webpageParser.parseWebpage(any())).thenReturn(successful(endDigits(expectedEndDigit)));

    assertThat(subject.fetchCurrentEndDigits(), isSuccessfulTryWithValue(hasProperty("endDigits", is(expectedEndDigit))));
  }

  @Test
  void shouldHandleFailureWhenFetchingWebpage() {
    when(webpageFetcher.get()).thenReturn(failure(new Exception("Something went wrong")));

    assertThat(subject.fetchCurrentEndDigits(), isFailedTryWithException(Exception.class));
  }

  @Test
  void shouldHandleFailureWhenParsingWebpage() {
    final String expectedEndDigit = "14";
    when(webpageFetcher.get()).thenReturn(successful(Jsoup.parse(String.format("<HTML><a>Slutsiffra just nu: %s</a></HTML>", expectedEndDigit))));
    when(webpageParser.parseWebpage(any())).thenReturn(failure(new NumberNotFoundException()));

    assertThat(subject.fetchCurrentEndDigits(), isFailedTryWithException(NumberNotFoundException.class));
  }

  @Test
  void shouldGetLatestEndDigits() {
    final EndDigitsEntity expectedEndDigits = EndDigitsEntity.create(endDigits("14"));
    when(repository.findTopByOrderByIdDesc()).thenReturn(Optional.of(expectedEndDigits));

    assertThat(subject.getLatestEndDigits(), isSuccessfulTryWithValue(is(expectedEndDigits)));
  }

  @Test
  void shouldHandleEmptyResponseWWhenGettingLatestEndDigits() {
    when(repository.findTopByOrderByIdDesc()).thenReturn(Optional.empty());

    assertThat(subject.getLatestEndDigits(), isFailedTryWithException(EntityNotFoundException.class));
  }

}