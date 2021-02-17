package com.mercadolibre.desafio_java.service;

import com.mercadolibre.desafio_java.model.product.Product;

public interface IStockService {
    void updateStock(Product product, Integer quantity);
    void verifyStock(Product product, Integer quantity);
}
