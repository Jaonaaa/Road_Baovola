package com.project.s5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.s5.models.Quality;
import com.project.s5.repository.QualityRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QualityService {

    private final QualityRepo qualityRepo;

    public void addQuality(Quality quality) {
        qualityRepo.save(quality);
    }

    public List<Quality> getAll() {
        return qualityRepo.findAll();
    }
}
