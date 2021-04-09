package com.example.enddigitsfetcher.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndDigitsDao extends JpaRepository<EndDigitsEntity, Long> {

  Optional<EndDigitsEntity> findTopByOrderByIdDesc();

}
