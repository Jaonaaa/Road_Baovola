package com.project.s5.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.s5.Utils.Status;
import com.project.s5.service.PriceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vente")
@RequiredArgsConstructor
public class VenteController {

    private final PriceService priceService;

    @GetMapping("/benefice/between/{min}/{max}")
    private Status getBetween(@PathVariable(name = "min") Double min, @PathVariable(name = "max") Double max) {
        try {
            return Status.builder().data(priceService.getRoadBetweenByBenefice(min, max)).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }
}
