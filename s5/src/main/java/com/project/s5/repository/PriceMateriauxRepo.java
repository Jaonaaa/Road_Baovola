package com.project.s5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Materiaux;
import com.project.s5.models.PriceMateriaux;

public interface PriceMateriauxRepo extends JpaRepository<PriceMateriaux, Long> {

    Optional<PriceMateriaux> findFirstByMateriaux(Materiaux materiaux);

}
