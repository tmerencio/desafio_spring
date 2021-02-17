package com.mercadolibre.desafio_java.service.impl;

import com.mercadolibre.desafio_java.exception.NoStockException;
import com.mercadolibre.desafio_java.model.product.Product;
import com.mercadolibre.desafio_java.repository.IProductRepository;
import com.mercadolibre.desafio_java.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements IStockService {
    @Autowired
    IProductRepository iProductRepository;

    @Override
    public void updateStock(Product product, Integer quantity) {
        verifyStock(product, quantity);
        iProductRepository.updateStock(product.getId(), quantity);
    }

    @Override
    public void verifyStock(Product product, Integer quantity) {
        if (product.getStock() < quantity) {
            throw new NoStockException(product.getStock(), product.getName());
        }
    }
}
