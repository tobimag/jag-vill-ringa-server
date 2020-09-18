package com.example.numberfetcher.service;

import static com.jasongoodwin.monads.Try.successful;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

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

  @InjectMocks
  EndDigitsFetcher subject;

  @Test
  void shouldGetEndDigits() {
    final String expectedEndDigit = "14";
    when(webpageFetcher.get()).thenReturn(successful(Jsoup.parse(String.format("<HTML><a>Slutsiffra just nu: %s</a></HTML>", expectedEndDigit))));
    assertThat(subject.fetchEndDigits().getUnchecked(), is(expectedEndDigit));
  }

}