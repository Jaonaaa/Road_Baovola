package com.project.s5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Quality;
import com.project.s5.models.TimeRoad;
import com.project.s5.models.Type;
import com.project.s5.models.TypeEntreprise;

public interface TypeEntrepriseRepo extends JpaRepository<TypeEntreprise, Long> {

    // Optional<TimeRoad> findFirstByQualityAndType(Quality quality, Type type);
}
