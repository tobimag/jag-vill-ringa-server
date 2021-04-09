package com.example.enddigitsfetcher.repository;

import static javax.persistence.GenerationType.IDENTITY;

import com.example.enddigitsfetcher.domain.valueobject.EndDigits;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "end_digits")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
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

  public EndDigitsEntity (String endDigits, OffsetDateTime timestamp) {
    this.endDigits = endDigits;
    this.timestamp = timestamp;
  }

  public static EndDigitsEntity create(EndDigits endDigits) {
    return new EndDigitsEntity(endDigits.getValue(), OffsetDateTime.now());
  }

}
