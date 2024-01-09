package com.project.s5.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.s5.Request.RoadStruct;
import com.project.s5.Response.RoadPriced;
import com.project.s5.Utils.Status;
import com.project.s5.models.Materiaux;
import com.project.s5.models.PriceMateriaux;
import com.project.s5.models.RoadMateriaux;
import com.project.s5.models.RoadTypeQuality;

import com.project.s5.repository.MateriauxRepo;
import com.project.s5.repository.PriceMateriauxRepo;
import com.project.s5.service.RoadService;
import com.project.s5.service.RoadStructureService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/road_mat")
@RequiredArgsConstructor
public class RoadMateriauxController {

    private final RoadService roadService;
    private final RoadStructureService roadStructureService;
    private final PriceMateriauxRepo priceMateriauxRepo;
    private final MateriauxRepo materiauxRepo;

    // ajout de type de route
    @PostMapping
    private Status addTypeMateriauxQuality(@RequestBody List<RoadStruct> roadStructs) {
        try {
            roadService.addStructRoad(roadStructs);
            return Status.builder().details("Struct added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    // get all materiaux
    @GetMapping("mat/{id_materiaux}")
    private Status getRoadByMateriaux(@PathVariable(name = "id_materiaux") Long idMateriaux) {
        try {
            List<RoadMateriaux> roadMateriauxs = roadStructureService.getRoadByMateriaux(idMateriaux);
            return Status.builder().data(roadMateriauxs).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping("mat/compare/{min}/{max}")
    public Status getRoadBeetwen(@PathVariable(name = "min") Double min, @PathVariable(name = "max") Double max) {
        try {
            System.out.println(min + " " + max);
            List<RoadPriced> roadMateriauxs = roadStructureService.getRoadBetween(min, max);
            return Status.builder().data(roadMateriauxs).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    //
    @PostMapping("mat/")
    private Status getRoadByMateriaux(@RequestBody RoadTypeQuality roadTypeQuality) {
        try {
            List<RoadMateriaux> roadMateriauxs = roadStructureService.getByRoadQuality(roadTypeQuality);
            return Status.builder().data(roadMateriauxs).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    //
    @PostMapping("price/")
    private Status addPriceMaterial(@RequestBody PriceMateriaux priceMateriaux) {
        try {
            priceMateriaux.setUnit(1.0);
            if (priceMateriaux.getPrice() <= 0)
                throw new RuntimeException("The Price can't be negative or equal to zero.");
            //

            Materiaux materiaux = materiauxRepo.findById(priceMateriaux.getMateriaux().getId()).get();
            if (priceMateriauxRepo.findFirstByMateriaux(materiaux).isPresent())
                throw new RuntimeException(materiaux.getName() + " has already a price");
            //
            // :
            priceMateriauxRepo.save(priceMateriaux);
            return Status.builder().details("Price added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

}
