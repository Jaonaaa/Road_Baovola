package com.project.s5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.MainOuevre;
import com.project.s5.models.RoadTypeQuality;

public interface MainOuevreRepo extends JpaRepository<MainOuevre, Long> {

    List<MainOuevre> findAllByRoadTypeQuality(RoadTypeQuality roadTypeQuality);

}
