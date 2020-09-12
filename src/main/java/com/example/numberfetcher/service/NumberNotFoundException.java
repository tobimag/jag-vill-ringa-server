package com.example.numberfetcher.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NumberNotFoundException extends RuntimeException {

  NumberNotFoundException() {
    super("Number not found while parsing web page!");
  }

}
