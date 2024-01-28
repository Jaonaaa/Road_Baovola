package com.project.s5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Entreprise;

public interface EntrepriseRepo extends JpaRepository<Entreprise, Long> {

}
