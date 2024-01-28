package com.project.s5.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.s5.Utils.Status;
import com.project.s5.models.MainOuevre;
import com.project.s5.models.Outils;
import com.project.s5.models.OutilsBySize;
import com.project.s5.models.PriceVente;
import com.project.s5.models.TimeByQuality;
import com.project.s5.service.OutilsService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/outils")
@RequiredArgsConstructor
public class OutilsController {

    private final OutilsService outilsService;

    @GetMapping
    private Status getAllOutils() {
        try {
            return Status.builder().data(outilsService.getAllOutils()).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping("/main_oeuvre/{id}")
    private Status getMainOeuvre(@PathVariable(name = "id") Long id) {
        try {
            return Status.builder().data(outilsService.getMainOeuvre(id)).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping("/price_vente")
    private Status getAllPriceVente() {
        try {
            return Status.builder().data(outilsService.getAllPriceVente()).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping("/outils_by_size")
    private Status getAllOutilsBySize() {
        try {
            return Status.builder().data(outilsService.getAllOutilsBySize()).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping("/time_qualities")
    private Status getAllTimeByQualities() {
        try {
            return Status.builder().data(outilsService.getAllTimeByQualities()).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @PostMapping
    private Status addOutils(@RequestBody Outils outils) {
        try {
            outilsService.addOutils(outils);
            return Status.builder().details("Outils " + outils.getName() + " added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @PostMapping("/sell_price")
    private Status addPriceVente(@RequestBody PriceVente priceVente) {
        try {
            outilsService.addPriceVente(priceVente);
            return Status.builder().details("Price of selling added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @PostMapping("/outils_by_size")
    private Status addOutilsBySize(@RequestBody OutilsBySize outilsBySize) {
        try {
            outilsService.addOutilsBySize(outilsBySize);
            return Status.builder().details("Nombre de outils " + outilsBySize.getQuantity() + " added").status("ok")
                    .build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @PostMapping("/time_by_quality")
    private Status addTimeByQuality(@RequestBody TimeByQuality timeByQuality) {
        try {
            outilsService.addTimeByQuality(timeByQuality);
            return Status.builder().details("Time added " + timeByQuality.getTime_to_work() + "h").status("ok")
                    .build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @PostMapping("/main_ouevre")
    private Status addMainOuevre(@RequestBody MainOuevre mainOuevre) {
        try {
            outilsService.addMainOuevre(mainOuevre);
            return Status.builder().details(" Main d'oeuvre added ").status("ok")
                    .build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

}
