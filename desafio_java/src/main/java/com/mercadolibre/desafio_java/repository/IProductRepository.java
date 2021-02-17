package com.mercadolibre.desafio_java.repository;

import com.mercadolibre.desafio_java.model.product.Product;

import java.util.List;

public interface IProductRepository {
    List<Product> findAll();
    Product findById(Long id);
    void updateStock(Long id, Integer quantity);
}
