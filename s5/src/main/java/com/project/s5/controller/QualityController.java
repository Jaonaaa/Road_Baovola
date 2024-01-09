package com.project.s5.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.s5.Utils.Status;
import com.project.s5.models.Quality;
import com.project.s5.service.QualityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/qt")
@RequiredArgsConstructor
public class QualityController {

    private final QualityService qualityService;

    @GetMapping
    private Status getAllQuality() {
        try {
            return Status.builder().data(qualityService.getAll()).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @PostMapping
    private Status addQuality(@RequestBody Quality quality) {
        try {
            qualityService.addQuality(quality);
            return Status.builder().details("Quality added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

}
