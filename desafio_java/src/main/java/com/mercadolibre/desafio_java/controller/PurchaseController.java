package com.mercadolibre.desafio_java.controller;

import com.mercadolibre.desafio_java.model.dto.request.AddToCartDTO;
import com.mercadolibre.desafio_java.model.dto.request.PurchaseRequestDTO;
import com.mercadolibre.desafio_java.service.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/purchase_request")
public class PurchaseController {

    @Autowired
    IPurchaseService iPurchaseService;

    @PostMapping(value = "")
    public ResponseEntity<Map<String, Object>> generatePurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        return new ResponseEntity<>(iPurchaseService.generatePurchase(purchaseRequestDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Map<String, Object>> addProduct(@RequestBody AddToCartDTO addToCartDTO) {
        return new ResponseEntity<>(iPurchaseService.addProduct(addToCartDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/confirm")
    public ResponseEntity<Map<String, Object>> confirmPurchase(@RequestBody Long id) {
        return new ResponseEntity<>(iPurchaseService.confirmPurchase(id), HttpStatus.OK);
    }
}
