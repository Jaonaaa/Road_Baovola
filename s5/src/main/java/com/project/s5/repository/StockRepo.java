package com.project.s5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Materiaux;
import com.project.s5.models.Stock;

public interface StockRepo extends JpaRepository<Stock, Long> {

    Optional<Stock> findFirstByMateriaux(Materiaux materiaux);

}
