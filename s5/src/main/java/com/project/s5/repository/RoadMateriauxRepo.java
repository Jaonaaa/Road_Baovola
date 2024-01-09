package com.project.s5.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Materiaux;
import com.project.s5.models.Quality;
import com.project.s5.models.RoadMateriaux;
import com.project.s5.models.RoadTypeQuality;
import com.project.s5.models.SizeRoad;
import com.project.s5.models.Type;

public interface RoadMateriauxRepo extends JpaRepository<RoadMateriaux, Long> {

    Optional<RoadMateriaux> findFirstByRoadTypeQualityAndMateriaux(RoadTypeQuality roadTypeQuality,
            Materiaux materiaux);

    List<RoadMateriaux> findByMateriaux(Materiaux materiaux);

    List<RoadMateriaux> findByRoadTypeQuality(RoadTypeQuality roadTypeQuality);

}
