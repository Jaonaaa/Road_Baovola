package com.project.s5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.PriceVente;
import com.project.s5.models.RoadTypeQuality;

public interface PriceVenteRepo extends JpaRepository<PriceVente, Long> {

    Optional<PriceVente> findFirstByRoadTypeQuality(RoadTypeQuality roadTypeQuality);

}
