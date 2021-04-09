package com.example.enddigitsfetcher.service.enddigitsservice;

import static com.example.enddigitsfetcher.domain.valueobject.matcher.FetchedEndDigitsWithEndDigitMatcher.equalsFetchedEndDigits;
import static com.example.enddigitsfetcher.domain.valueobject.matcher.FetchedEndDigitsWithEndDigits.isFetchedEndDigitsWithValue;
import static com.example.enddigitsfetcher.matcher.FailedTryWithException.isFailedTryWithException;
import static com.example.enddigitsfetcher.matcher.SuccessfulTryWithValue.isSuccessfulTryWith;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.example.enddigitsfetcher.domain.FetchedEndDigits;
import com.example.enddigitsfetcher.domain.valueobject.EndDigits;
import com.example.enddigitsfetcher.repository.EndDigitsRepository;
import com.example.enddigitsfetcher.service.webpagefetcher.WebpageFetcher;
import com.example.enddigitsfetcher.service.webpagefetcher.WebpageParser;
import com.jasongoodwin.monads.Try;
import java.time.OffsetDateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

  final String expectedEndDigit = "14";
  final Document webpage = Jsoup.parse(String.format("<HTML><a>Slutsiffra just nu: %s</a></HTML>", expectedEndDigit));
  final EndDigits endDigits = new EndDigits(expectedEndDigit);
  final FetchedEndDigits fetchedEndDigits = new FetchedEndDigits(endDigits, OffsetDateTime.now());

  @Test
  void shouldStoreEndDigits() {

    when(webpageFetcher.getWebpage()).thenReturn(Try.successful(webpage));
    when(webpageParser.parseWebpage(eq(webpage))).thenReturn(Try.successful(endDigits));
    when(repository.save(argThat(equalsFetchedEndDigits(endDigits)))).thenReturn(Try.successful(fetchedEndDigits));

    Try<FetchedEndDigits> result = subject.storeCurrentEndDigits();

    assertThat(result, isSuccessfulTryWith(is(fetchedEndDigits)));

  }

  @Test
  void shouldHandleFailureWhenGettingWebpageWhenStoringEndDigits() {

    when(webpageFetcher.getWebpage()).thenReturn(Try.failure(new RuntimeException()));

    Try<FetchedEndDigits> result = subject.storeCurrentEndDigits();

    assertThat(result, isFailedTryWithException(RuntimeException.class));

  }

  @Test
  void shouldHandleFailureWhenParsingWhenStoringEndDigits() {

    when(webpageFetcher.getWebpage()).thenReturn(Try.successful(webpage));
    when(webpageParser.parseWebpage(eq(webpage))).thenReturn(Try.failure(new RuntimeException()));

    Try<FetchedEndDigits> result = subject.storeCurrentEndDigits();

    assertThat(result, isFailedTryWithException(RuntimeException.class));

  }

  @Test
  void shouldHandleFailureWhenSavingWhenStoringEndDigits() {

    when(webpageFetcher.getWebpage()).thenReturn(Try.successful(webpage));
    when(webpageParser.parseWebpage(eq(webpage))).thenReturn(Try.successful(endDigits));
    when(repository.save(argThat(equalsFetchedEndDigits(endDigits)))).thenReturn(Try.failure(new RuntimeException()));

    Try<FetchedEndDigits> result = subject.storeCurrentEndDigits();

    assertThat(result, isFailedTryWithException(RuntimeException.class));

  }

  @Test
  void shouldFetchEndDigits() {

    when(webpageFetcher.getWebpage()).thenReturn(Try.successful(webpage));
    when(webpageParser.parseWebpage(eq(webpage))).thenReturn(Try.successful(endDigits));

    Try<FetchedEndDigits> result = subject.fetchCurrentEndDigits();

    assertThat(result, isSuccessfulTryWith(isFetchedEndDigitsWithValue(expectedEndDigit)));

  }

  @Test
  void shouldHandleFailureWhenGettingWebpageWhenFetchingEndDigits() {

    when(webpageFetcher.getWebpage()).thenReturn(Try.failure(new RuntimeException()));

    Try<FetchedEndDigits> result = subject.fetchCurrentEndDigits();

    assertThat(result, isFailedTryWithException(RuntimeException.class));

  }

  @Test
  void shouldHandleFailureWhenParsingWebpageWhenFetchingEndDigits() {

    when(webpageFetcher.getWebpage()).thenReturn(Try.successful(webpage));
    when(webpageParser.parseWebpage(eq(webpage))).thenReturn(Try.failure(new RuntimeException()));

    Try<FetchedEndDigits> result = subject.storeCurrentEndDigits();

    assertThat(result, isFailedTryWithException(RuntimeException.class));

  }

  @Test
  void shouldGetLatestEndDigits() {

    when(repository.getLatestEndDigits()).thenReturn(Try.successful(fetchedEndDigits));

    Try<FetchedEndDigits> result = subject.getLatestEndDigits();

    assertThat(result, isSuccessfulTryWith(is(fetchedEndDigits)));

  }

  @Test
  void shouldHandleFailureWhenGettingLatestEndDigits() {

    when(repository.getLatestEndDigits()).thenReturn(Try.failure(new RuntimeException()));

    Try<FetchedEndDigits> result = subject.getLatestEndDigits();

    assertThat(result, isFailedTryWithException(RuntimeException.class));

  }
}