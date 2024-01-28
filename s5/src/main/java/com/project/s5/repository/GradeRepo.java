package com.project.s5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Grade;

public interface GradeRepo extends JpaRepository<Grade, Long> {

}
