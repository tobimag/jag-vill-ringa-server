package com.example.enddigitsfetcher.resource;

import static lombok.AccessLevel.PUBLIC;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PUBLIC, makeFinal = true)
@AllArgsConstructor
public class EndDigitsResponse {

  String digits;
  OffsetDateTime timestamp;

}
