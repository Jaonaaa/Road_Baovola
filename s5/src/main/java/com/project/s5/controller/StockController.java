package com.project.s5.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.s5.Request.BuyStock;
import com.project.s5.Utils.Status;
import com.project.s5.models.Supplier;
import com.project.s5.service.TradeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final TradeService tradeService;

    @GetMapping("/supplier")
    private Status getAllSupplier() {
        try {
            return Status.builder().data(tradeService.getAllSupplier()).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping("/mv")
    private Status getAllMovement() {
        try {
            return Status.builder().data(tradeService.getAllSupplier()).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping
    private Status getAllStock() {
        try {
            return Status.builder().data(tradeService.getAllStock()).status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @PostMapping("/supplier")
    private Status addSupplier(@RequestBody Supplier supplier) {
        try {
            tradeService.registerSuupplier(supplier);
            return Status.builder().details("Supplier added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @PostMapping
    private Status addStock(@RequestBody BuyStock buyStock) {
        try {
            System.out.println(buyStock.toString());
            tradeService.buyMatiere(buyStock.getMateriaux(), buyStock.getSupplier(), buyStock.getQuantity());
            return Status.builder().details("Stock added").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }
}
