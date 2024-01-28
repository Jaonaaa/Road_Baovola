package com.project.s5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.RoadTypeQuality;
import com.project.s5.models.Vente;

public interface VenteRepo extends JpaRepository<Vente, Long> {

    List<Vente> findAllByRoadTypeQuality(RoadTypeQuality roadTypeQuality);

}
