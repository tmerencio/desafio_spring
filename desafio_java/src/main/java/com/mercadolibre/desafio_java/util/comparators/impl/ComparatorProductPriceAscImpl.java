package com.mercadolibre.desafio_java.util.comparators.impl;

import com.mercadolibre.desafio_java.model.dto.response.ProductDTO;

import java.util.Comparator;

public class ComparatorProductPriceAscImpl implements Comparator<ProductDTO> {
    @Override
    public int compare(ProductDTO a, ProductDTO b) {
        return (int) (a.getPrice() - b.getPrice());
    }
}
