package com.mercadolibre.desafio_java.repository;

import com.mercadolibre.desafio_java.model.dto.response.ReceiptDTO;

import java.util.List;

public interface IPurchaseRepository {
    List<ReceiptDTO> findAll();
    void save(ReceiptDTO receiptDTO);
    Long nextId();
}
