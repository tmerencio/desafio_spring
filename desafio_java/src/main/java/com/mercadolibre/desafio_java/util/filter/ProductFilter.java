package com.mercadolibre.desafio_java.util.filter;

import com.mercadolibre.desafio_java.model.dto.response.ProductDTO;
import com.mercadolibre.desafio_java.model.dto.request.ProductSearchDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ProductFilter {

    public static Predicate<ProductDTO> getInstance(ProductSearchDTO productSearchDTO) {
        return getFilters(productSearchDTO).stream().reduce(p -> true, Predicate::and);
    }

    public static List<Predicate<ProductDTO>> getFilters(ProductSearchDTO productSearchDTO) {
        List<Predicate<ProductDTO>> filters = new ArrayList<>();

        if (productSearchDTO.getCategory() != null) {
            filters.add(productDTO -> productDTO.getCategory().equals(productSearchDTO.getCategory()));
        }

        if (productSearchDTO.getBrand() != null) {
            filters.add(productDTO -> productDTO.getBrand().equals(productSearchDTO.getBrand()));
        }

        if (productSearchDTO.getFreeShipping() != null) {
            filters.add(productDTO -> productDTO.getFreeShipping().equals(productSearchDTO.getFreeShipping()));
        }

        if (productSearchDTO.getPriceTopLimit() != null) {
            filters.add(productDTO -> productDTO.getPrice() <= productSearchDTO.getPriceTopLimit());
        }

        if (productSearchDTO.getPriceBelowLimit() != null) {
            filters.add(productDTO -> productDTO.getPrice() >= productSearchDTO.getPriceBelowLimit());
        }

        if (productSearchDTO.getPrestige() != null) {
            filters.add(productDTO -> productDTO.getPrestige() >= productSearchDTO.getPrestige());
        }

        return filters;
    }
}
