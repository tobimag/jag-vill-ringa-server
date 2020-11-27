package com.example.enddigitsfetcher.repository;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "end_digits")
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
public class EndDigitsEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "digits")
  @Getter
  private String endDigits;

  @Column(name = "timestamp")
  @Getter
  private OffsetDateTime timestamp;

  private EndDigitsEntity (String endDigits, OffsetDateTime timestamp) {
    this.endDigits = endDigits;
    this.timestamp = timestamp;
  }

  public static EndDigitsEntity create(String nedDigits) {
    return new EndDigitsEntity(nedDigits, OffsetDateTime.now());
  }

}
