package com.project.s5.service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.project.s5.models.Entreprise;

import com.project.s5.models.TypeEntreprise;
import com.project.s5.repository.EntrepriseRepo;

import com.project.s5.repository.TypeEntrepriseRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final EntrepriseRepo entrepriseRepo;
    private final TypeEntrepriseRepo typeEntrepriseRepo;

    // GET

    public List<Entreprise> getAllEntreprises() {
        return entrepriseRepo.findAll();
    }

    public List<TypeEntreprise> getAllTypeEntreprise() {
        return typeEntrepriseRepo.findAll();
    }

    // POST

    public void addEntreprise(Entreprise entreprise) {
        entrepriseRepo.save(entreprise);
    }

    public void addTypeEntreprise(TypeEntreprise typeEntreprise) {
        typeEntrepriseRepo.save(typeEntreprise);
    }

}
