package com.example.enddigitsfetcher.repository;

import static com.example.enddigitsfetcher.domain.valueobject.EndDigits.endDigits;
import static com.example.enddigitsfetcher.matcher.FailedTryWithException.isFailedTryWithException;
import static com.example.enddigitsfetcher.matcher.SuccessfulTryWithValue.isSuccessfulTryWith;
import static java.time.OffsetDateTime.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.example.enddigitsfetcher.domain.FetchedEndDigits;
import com.jasongoodwin.monads.Try;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EndDigitsRepositoryTest {

  private static final String END_DIGITS = "42";
  private static final OffsetDateTime TIMESTAMP = now();
  private static final EndDigitsEntity END_DIGITS_ENTITY = new EndDigitsEntity(END_DIGITS, TIMESTAMP);
  private static final FetchedEndDigits FETCHED_END_DIGITS = new FetchedEndDigits(endDigits(END_DIGITS).getUnchecked(), TIMESTAMP);

  @Mock
  EndDigitsDao dao;

  @InjectMocks
  EndDigitsRepository subject;

  @Test
  void shouldSaveFetchedEndDigits() {

    when(dao.save(any())).thenReturn(END_DIGITS_ENTITY);

    Try<FetchedEndDigits> result = subject.save(FETCHED_END_DIGITS);

    assertThat(result, isSuccessfulTryWith(is(FETCHED_END_DIGITS)));

  }

  @Test
  void shouldHandleFailureWhenSavingEndDigits() {

    when(dao.save(any())).thenThrow(new RuntimeException());

    Try<FetchedEndDigits> result = subject.save(FETCHED_END_DIGITS);

    assertThat(result, isFailedTryWithException(RuntimeException.class));

  }

  @Test
  void shouldReturnFetchedEndDigits() {

    when(dao.findTopByOrderByIdDesc()).thenReturn(Optional.of(END_DIGITS_ENTITY));

    Try<FetchedEndDigits> result = subject.getLatestEndDigits();

    assertThat(result, isSuccessfulTryWith(is(FETCHED_END_DIGITS)));

  }

  @Test
  void shouldHandleFailureWhenGettingEndDigits() {

    when(dao.findTopByOrderByIdDesc()).thenThrow(new RuntimeException());

    Try<FetchedEndDigits> result = subject.getLatestEndDigits();

    assertThat(result, isFailedTryWithException(RuntimeException.class));

  }


}