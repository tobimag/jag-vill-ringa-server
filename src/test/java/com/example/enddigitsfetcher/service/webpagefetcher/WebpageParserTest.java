package com.example.enddigitsfetcher.service.webpagefetcher;

import static com.example.enddigitsfetcher.domain.valueobject.EndDigits.endDigits;
import static com.example.enddigitsfetcher.domain.valueobject.matcher.EndDigitsWithValue.isEndDigitsWithValue;
import static com.example.enddigitsfetcher.matcher.FailedTryWithException.isFailedTryWithException;
import static com.example.enddigitsfetcher.matcher.SuccessfulTryWithValue.isSuccessfulTryWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.example.enddigitsfetcher.domain.valueobject.EndDigits;
import com.example.enddigitsfetcher.exception.EndDigitsParsingException;
import com.jasongoodwin.monads.Try;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
    final Document htmlPage = Jsoup.parse(String.format("<HTML><a>Slutsiffra just nu: %s</a></HTML>", expectedEndDigits));

    Try<EndDigits> result = subject.parseWebpage(htmlPage);

    assertThat(result, isSuccessfulTryWith(isEndDigitsWithValue(expectedEndDigits)));

  }

  @Test
  void shouldHandleFailure() {

    final Document htmlPage = Jsoup.parse("<HTML><a>SlutsiffraBATMAN just nu: 14</a></HTML>");

    Try<EndDigits> result = subject.parseWebpage(htmlPage);

    assertThat(result, isFailedTryWithException(EndDigitsParsingException.class));

  }

}
