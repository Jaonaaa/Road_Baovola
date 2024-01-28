package com.project.s5.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.s5.Utils.Status;
import com.project.s5.models.SizeRoad;
import com.project.s5.models.TimeRoad;
import com.project.s5.models.Type;
import com.project.s5.models.TypeMateriaux;
import com.project.s5.service.RoadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/road")
@RequiredArgsConstructor
public class RoadController {

    private final RoadService roadService;

    // ajout de type de route
    @PostMapping("/type")
    private Status addType(@RequestBody Type type) {
        try {
            roadService.addType(type);
            return Status.builder().details("Type added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    // ajout des materiaux consistant un type de route
    @PostMapping("/type_materiaux")
    private Status addMateriauxType(@RequestBody List<TypeMateriaux> mats) {
        try {
            roadService.addAllMaterials(mats);
            return Status.builder().details("Type material added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    // ajout de temps nécessaire par m² rapport au type et qualité
    @PostMapping("/time/qt/type")
    private Status addTimeType(@RequestBody TimeRoad timeRoad) {
        try {
            roadService.addTimeQualityType(timeRoad);
            return Status.builder().details("Time/m² road added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    // ajout de taille des routes
    @PostMapping("/size")
    private Status addSize(@RequestBody SizeRoad size) {
        try {
            roadService.addSizeRoad(size);
            return Status.builder().details("size  added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    // ajout de taille des routes
    @GetMapping("/size")
    private Status getAllSize() {
        try {
            return Status.builder().data(roadService.getAllSize()).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    // get all road type quality
    @GetMapping("/type_quality")
    private Status getAllRoadTypeQuality() {
        try {
            return Status.builder().data(roadService.getAllRoadTypeQuality()).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    // select tout les materaux utilisé pour une type de route
    @GetMapping("/type_materiaux/{id}")
    private Status getMaterialType(@PathVariable(name = "id") Long id_type) {
        try {
            return Status.builder().details("").status("ok").data(roadService.getAllTypeMaterial(id_type))
                    .build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

}
