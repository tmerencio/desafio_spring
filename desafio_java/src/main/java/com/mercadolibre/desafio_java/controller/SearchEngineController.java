package com.mercadolibre.desafio_java.controller;

import com.mercadolibre.desafio_java.model.dto.response.ProductDTO;
import com.mercadolibre.desafio_java.model.dto.request.ProductSearchDTO;
import com.mercadolibre.desafio_java.service.ISearchEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class SearchEngineController {
    @Autowired
    ISearchEngineService iSearchEngineService;

    public SearchEngineController(ISearchEngineService iSearchEngineService) {
        this.iSearchEngineService = iSearchEngineService;
    }

    @GetMapping(value = "/articles")
    public ResponseEntity<List<ProductDTO>> getProducts(ProductSearchDTO productSearchDTO) {
        List<ProductDTO> filteredProducts = iSearchEngineService.findProducts(productSearchDTO);
        return new ResponseEntity<>(filteredProducts, HttpStatus.OK);
    }
}
