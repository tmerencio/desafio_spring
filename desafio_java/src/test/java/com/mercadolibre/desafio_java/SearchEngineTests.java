package com.mercadolibre.desafio_java;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_java.model.dto.request.ProductSearchDTO;
import com.mercadolibre.desafio_java.model.dto.response.ProductDTO;
import com.mercadolibre.desafio_java.model.product.Product;
import com.mercadolibre.desafio_java.repository.IProductRepository;
import com.mercadolibre.desafio_java.service.ISearchEngineService;
import com.mercadolibre.desafio_java.service.impl.SearchEngineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchEngineTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductRepository iProductRepository;

    @Mock
    private ISearchEngineService searchEngineService;

    private ObjectMapper objectMapper;

    private List<Product> productList4;
    private List<Product> productList8;
    private List<Product> alphDisorderdProducts;
    private List<Product> alphAscOrderedProducts;

    private List<ProductDTO> productDTOList;

    private String productListJson;
    private String categoryFilteredProductJson;
    private String multipleFiltersProductJson;
    private String alphDescOrderedProductJson;
    private String alphAscOrderedProductJson;
    private String priceDescOrderedProductJson;
    private String priceAscOrderedProductJson;


    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();

        this.searchEngineService = new SearchEngineServiceImpl(iProductRepository);

        this.productList4 = this.getProductsFrom("src/test/data/4_products.json");
        this.productDTOList = this.productsToDTOList(productList4);
        this.productListJson = this.stringValue(productDTOList);

        List<Product> categoryFilteredProducts = this.getProductsFrom("src/test/data/category_filtered_products.json");
        List<ProductDTO> categoryFilteredProductDTOList = this.productsToDTOList(categoryFilteredProducts);
        this.categoryFilteredProductJson = this.stringValue(categoryFilteredProductDTOList);

        this.productList8 = this.getProductsFrom("src/test/data/8_products.json");

        List<Product> multipleFiltersProducts = this.getProductsFrom("src/test/data/multiple_filters_products.json");
        List<ProductDTO> multipleFiltersProductDTOList = this.productsToDTOList(multipleFiltersProducts);
        this.multipleFiltersProductJson = this.stringValue(multipleFiltersProductDTOList);

        this.alphDisorderdProducts = this.getProductsFrom("src/test/data/alph_disordered_products.json");

        List<Product> alphDescOrderedProducts = this.getProductsFrom("src/test/data/alph_desc_ordered_products.json");
        List<ProductDTO> alphDescOrderedProductDTOList = this.productsToDTOList(alphDescOrderedProducts);
        this.alphDescOrderedProductJson = this.stringValue(alphDescOrderedProductDTOList);

        this.alphAscOrderedProducts = this.getProductsFrom("src/test/data/alph_asc_ordered_products.json");
        List<ProductDTO> alphAscOrderedProductDTOList = this.productsToDTOList(alphAscOrderedProducts);
        this.alphAscOrderedProductJson = this.stringValue(alphAscOrderedProductDTOList);

        List<Product> priceDescOrderedProducts = this.getProductsFrom("src/test/data/price_desc_ordered_products.json");
        List<ProductDTO> priceDescOrderedProductDTOList = this.productsToDTOList(priceDescOrderedProducts);
        this.priceDescOrderedProductJson = this.stringValue(priceDescOrderedProductDTOList);

        List<Product> priceAscOrderedProducts = this.getProductsFrom("src/test/data/price_asc_ordered_products.json");
        List<ProductDTO> priceAscOrderedProductDTOList = this.productsToDTOList(priceAscOrderedProducts);
        this.priceAscOrderedProductJson = this.stringValue(priceAscOrderedProductDTOList);
    }

    @Test
    void shouldGetMockedProducts() {
        when(iProductRepository.findAll()).thenReturn(productList4);
        List<ProductDTO> returnedProductsDTO = searchEngineService.findProducts(new ProductSearchDTO());

        verify(iProductRepository, atLeast(1)).findAll();
        assertThat(returnedProductsDTO).isEqualTo(productDTOList);
    }

    @Test
    void shouldReturnMockedProductsTest() throws Exception {
        when(iProductRepository.findAll()).thenReturn(productList4);
        this.mockMvc.perform(get("/api/v1/articles")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(productListJson));
    }

    @Test
    void shouldReturnFilteredByCategoryProductsTest() throws Exception {
        when(iProductRepository.findAll()).thenReturn(productList4);
        this.mockMvc.perform(get("/api/v1/articles")
                .queryParam("category", "Herramientas")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(categoryFilteredProductJson));
    }

    @Test
    void shouldReturnMultipleFiltersProductsTest() throws Exception {
        when(iProductRepository.findAll()).thenReturn(productList8);
        this.mockMvc.perform(get("/api/v1/articles")
                .queryParam("category", "Herramientas")
                .queryParam("freeShipping", "true")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(multipleFiltersProductJson));
    }

    @Test
    void shouldReturnAlphDescOrderedProductsTest() throws Exception {
        when(iProductRepository.findAll()).thenReturn(alphDisorderdProducts);
        this.mockMvc.perform(get("/api/v1/articles")
                .queryParam("order", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(alphDescOrderedProductJson));
    }

    @Test
    void shouldReturnAlphAscOrderedProductsTest() throws Exception {
        when(iProductRepository.findAll()).thenReturn(alphAscOrderedProducts);
        this.mockMvc.perform(get("/api/v1/articles")
                .queryParam("order", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(alphAscOrderedProductJson));
    }

    @Test
    void shouldReturnPriceDescOrderedProductsTest() throws Exception {
        when(iProductRepository.findAll()).thenReturn(productList4);
        this.mockMvc.perform(get("/api/v1/articles")
                .queryParam("order", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(priceDescOrderedProductJson));
    }

    @Test
    void shouldReturnPriceAscOrderedProductsTest() throws Exception {
        when(iProductRepository.findAll()).thenReturn(productList4);
        this.mockMvc.perform(get("/api/v1/articles")
                .queryParam("order", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(priceAscOrderedProductJson));
    }

    private List<Product> getProductsFrom(String filePath) {
        File file = null;

        try {
            file = ResourceUtils.getFile(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TypeReference<List<Product>> typeReference = new TypeReference<>() {};
        List<Product> productList = null;

        try {
            productList = this.objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productList;
    }

    private List<ProductDTO> productsToDTOList(List<Product> products) {
        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    private String stringValue(Object object) {
        try {
            return this.objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
