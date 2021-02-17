package com.mercadolibre.desafio_java.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_java.exception.NoDataFoundException;
import com.mercadolibre.desafio_java.exception.ProductNotFoundException;
import com.mercadolibre.desafio_java.model.product.Product;
import com.mercadolibre.desafio_java.repository.IProductRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements IProductRepository {

    private final String FILE_PATH = "src/main/resources/data/products.json";

    private List<Product> loadJsonFile() {
        File file = null;

        try {
            file = ResourceUtils.getFile(FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Product>> typeReference = new TypeReference<>() {};
        List<Product> productList = null;

        try {
            productList = objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productList;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = this.loadJsonFile();

        if (productList.isEmpty()) {
            throw new NoDataFoundException();
        }

        return productList;
    }

    @Override
    public Product findById(Long id) {
        return this.loadJsonFile()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public void updateStock(Long id, Integer quantity) {
        List<Product> productList = this.loadJsonFile();

        for(Product product : productList) {
            if(product.getId().equals(id)) {
                product.setStock(product.getStock() - quantity);
            }
        }

        this.updateJsonFile(productList);
    }

    private void updateJsonFile(List<Product> productList) {
        File file = null;

        try {
            file = ResourceUtils.getFile(FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(file, productList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
