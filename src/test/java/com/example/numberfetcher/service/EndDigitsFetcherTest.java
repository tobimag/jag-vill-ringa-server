package com.example.numberfetcher.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class EndDigitsFetcherTest {

  @Mock
  WebpageFetcher webpageFetcher;

  @InjectMocks
  EndDigitsFetcher subject;

  @Test
  void shouldGetEndDigits() {
    when(webpageFetcher.get()).thenReturn(null);

  }

}