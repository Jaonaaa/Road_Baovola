package com.project.s5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.OutilsBySize;
import com.project.s5.models.SizeRoad;

public interface OutilsBySizeRepo extends JpaRepository<OutilsBySize, Long> {

    Optional<OutilsBySize> findFirstBySizeRoad(SizeRoad sizeRoad);

}
