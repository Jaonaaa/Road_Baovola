package com.project.s5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {

}
