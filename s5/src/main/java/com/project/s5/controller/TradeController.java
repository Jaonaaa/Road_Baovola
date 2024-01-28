package com.project.s5.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.s5.Request.SellProduct;
import com.project.s5.Utils.Status;
import com.project.s5.service.TradeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    private Status sellProduct(@RequestBody List<SellProduct> sellProduct) {
        try {
            for (SellProduct sell : sellProduct) {
                tradeService.sellProduct(sell.getRoadTypeQuality(), sell.getQuantity(), sell.getEntreprise());
            }
            return Status.builder().details("Transaction effectued").status("ok").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }
}
