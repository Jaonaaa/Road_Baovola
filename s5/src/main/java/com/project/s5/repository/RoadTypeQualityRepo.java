package com.project.s5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Quality;
import com.project.s5.models.RoadTypeQuality;
import com.project.s5.models.SizeRoad;
import com.project.s5.models.Type;

public interface RoadTypeQualityRepo extends JpaRepository<RoadTypeQuality, Long> {

    Optional<RoadTypeQuality> findFirstByTypeAndQualityAndSize(Type type, Quality quality, SizeRoad sizeRoad);

}
