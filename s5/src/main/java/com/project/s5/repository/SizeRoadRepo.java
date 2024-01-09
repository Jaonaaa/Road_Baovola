package com.project.s5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Quality;
import com.project.s5.models.SizeRoad;
import com.project.s5.models.TimeRoad;
import com.project.s5.models.Type;

public interface SizeRoadRepo extends JpaRepository<SizeRoad, Long> {

    Optional<SizeRoad> findFirstByName(String name);

}
