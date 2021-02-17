package com.mercadolibre.desafio_java.service;

import com.mercadolibre.desafio_java.model.dto.request.AddToCartDTO;
import com.mercadolibre.desafio_java.model.dto.request.PurchaseRequestDTO;

import java.util.Map;

public interface IPurchaseService {
    Map<String, Object> generatePurchase(PurchaseRequestDTO purchaseRequestDTO);
    Map<String, Object> confirmPurchase(Long id);
    Map<String, Object> addProduct(AddToCartDTO addToCartDTO);
}
