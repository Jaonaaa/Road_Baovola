package com.project.s5.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.s5.Request.RoadMaterialRequest;
import com.project.s5.Utils.Status;
import com.project.s5.models.Materiaux;
import com.project.s5.models.PriceMateriaux;
import com.project.s5.models.Type;
import com.project.s5.repository.MateriauxRepo;
import com.project.s5.repository.PriceMateriauxRepo;
import com.project.s5.repository.TypeRepo;
import com.project.s5.service.MateriauxService;
import com.project.s5.service.RoadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/materiaux")
@RequiredArgsConstructor
public class MateriauxController {

    private final MateriauxRepo materiauxRepo;
    private final TypeRepo typeRepo;
    private final RoadService roadService;
    private final MateriauxService materiauxService;
    private final PriceMateriauxRepo priceMateriauxRepo;

    @GetMapping
    private Status getMaterial() {
        try {
            List<Materiaux> mateials = materiauxRepo.findAll();
            return Status.builder().details("Material").status("ok").data(mateials).build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping("/types")
    private Status getTypes() {
        try {
            List<Type> types = typeRepo.findAll();
            return Status.builder().status("ok").data(types).build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping("/price")
    private Status getMaterialPrice() {
        try {
            List<PriceMateriaux> mateials = priceMateriauxRepo.findAll();
            return Status.builder().details("Material").status("ok").data(mateials).build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @PostMapping
    private Status addMaterial(@RequestBody Materiaux materiaux) {
        try {
            roadService.addMaterial(materiaux);
            return Status.builder().details("Material Added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @PostMapping("/price")
    private Status addMaterialPrice(@RequestBody PriceMateriaux priceMateriaux) {
        try {
            materiauxService.addMaterialPrice(priceMateriaux);
            return Status.builder().details("Material price added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    // Ajout des quantity materiaux necessaire pour 1m² de route par rapport a sa
    // qualité
    @PostMapping("/road")
    private Status addMaterialRoadByType(@RequestBody RoadMaterialRequest roadMaterial) {
        try {
            materiauxService.addMaterialNecessaryForRoad(roadMaterial);
            return Status.builder().details("Done").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

}
