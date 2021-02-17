package com.mercadolibre.desafio_java.service.impl;

import com.mercadolibre.desafio_java.repository.IProductRepository;
import com.mercadolibre.desafio_java.model.dto.response.ProductDTO;
import com.mercadolibre.desafio_java.model.dto.request.ProductSearchDTO;
import com.mercadolibre.desafio_java.service.ISearchEngineService;
import com.mercadolibre.desafio_java.util.comparators.ComparatorFactory;
import com.mercadolibre.desafio_java.util.filter.ProductFilter;
import com.mercadolibre.desafio_java.util.sorter.Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchEngineServiceImpl implements ISearchEngineService {
    @Autowired
    IProductRepository iProductRepository;

    @Autowired
    Sorter<ProductDTO> sorter;

    public SearchEngineServiceImpl(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    @Override
    public List<ProductDTO> findProducts(ProductSearchDTO productSearchDTO) {

        List<ProductDTO> productDTOList = iProductRepository
                .findAll()
                .stream()
                .map(ProductDTO::new)
                .filter(ProductFilter.getInstance(productSearchDTO))
                .collect(Collectors.toList());

        //sorter.sort(productDTOList, ComparatorFactory.getInstance(productSearchDTO));
        return productDTOList;
    }
}
