package com.mercadolibre.desafio_java.service;

import com.mercadolibre.desafio_java.model.dto.response.ProductDTO;
import com.mercadolibre.desafio_java.model.dto.request.ProductSearchDTO;

import java.util.List;

public interface ISearchEngineService {
    List<ProductDTO> findProducts(ProductSearchDTO productSearchDTO);
}
