package com.example.enddigitsfetcher.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndDigitsRepository extends JpaRepository<EndDigitsEntity, Long> {

  Optional<EndDigitsEntity> findTopByOrderByIdDesc();

}
