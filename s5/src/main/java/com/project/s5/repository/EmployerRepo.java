package com.project.s5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Employer;
import com.project.s5.models.Outils;

public interface EmployerRepo extends JpaRepository<Employer, Long> {

    Optional<Employer> findAllByOutils(Outils outils);

}
