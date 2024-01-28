package com.project.s5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Quality;
import com.project.s5.models.TimeByQuality;

public interface TimeByQualityRepo extends JpaRepository<TimeByQuality, Long> {

    Optional<TimeByQuality> findFirstByQuality(Quality quality);

}
