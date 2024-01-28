package com.project.s5.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.s5.Response.Statistique;
import com.project.s5.Utils.Status;
import com.project.s5.models.Entreprise;
import com.project.s5.models.TypeEntreprise;
import com.project.s5.service.ClientService;
import com.project.s5.service.StatService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final StatService statService;

    @GetMapping
    private Status getAllEntreprise() {
        try {
            List<Entreprise> ent = clientService.getAllEntreprises();
            return Status.builder().status("ok").data(ent).build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping("/type")
    private Status getAllEntrepriseType() {
        try {
            List<TypeEntreprise> ent = clientService.getAllTypeEntreprise();
            return Status.builder().status("ok").data(ent).build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping("/stat/{id_road}")
    private Status getStat(@PathVariable(name = "id_road") Long idRoadTypeQuality) {
        try {
            List<Statistique> ent = statService.getStat(idRoadTypeQuality);
            return Status.builder().status("ok").data(ent).build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping("/stats")
    private Status getAllStat() {
        try {
            List<List<Statistique>> ent = statService.getStatAll();
            return Status.builder().status("ok").data(ent).build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    // POST

    @PostMapping
    private Status addEntreprise(@RequestBody Entreprise entreprise) {
        try {
            clientService.addEntreprise(entreprise);
            return Status.builder().details("entreprise Added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @PostMapping("/type")
    private Status addEntrepriseType(@RequestBody TypeEntreprise entreprise) {
        try {
            clientService.addTypeEntreprise(entreprise);
            return Status.builder().details("Type entreprise Added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }
}
