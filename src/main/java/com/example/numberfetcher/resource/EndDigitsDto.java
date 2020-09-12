package com.example.numberfetcher.resource;

import static lombok.AccessLevel.PUBLIC;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PUBLIC, makeFinal = true)
@AllArgsConstructor
public class EndDigitsDto {

  String digits;
  OffsetDateTime timestamp;

}
