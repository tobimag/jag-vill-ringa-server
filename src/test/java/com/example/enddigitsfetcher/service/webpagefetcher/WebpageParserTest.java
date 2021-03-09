package com.example.enddigitsfetcher.service.webpagefetcher;

import static com.example.enddigitsfetcher.matchers.IsFailedTryWithException.isFailedTryWithException;
import static com.example.enddigitsfetcher.matchers.IsSuccessfulTryWithValue.isSuccessfulTryWithValue;
import static com.example.enddigitsfetcher.service.valueobjects.EndDigits.endDigits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WebpageParserTest {

  @InjectMocks
  WebpageParser subject;

  @Test
  void shouldParseEndDigits() {
    final String expectedEndDigits = "14";
    assertThat(subject.parseWebpage(Jsoup.parse(String.format("<HTML><a>Slutsiffra just nu: %s</a></HTML>", expectedEndDigits))),
        isSuccessfulTryWithValue(is(endDigits(expectedEndDigits))));
  }

  @Test
  void shouldHandleFailure() {
    assertThat(subject.parseWebpage(Jsoup.parse("<HTML><a>SlutsiffraBATMAN just nu: 14</a></HTML>")), isFailedTryWithException(NumberNotFoundException.class));
  }

}
