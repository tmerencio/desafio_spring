package com.mercadolibre.desafio_java;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_java.model.dto.request.PurchaseDTO;
import com.mercadolibre.desafio_java.model.dto.request.PurchaseRequestDTO;
import com.mercadolibre.desafio_java.model.dto.response.ReceiptDTO;
import com.mercadolibre.desafio_java.model.product.Product;
import com.mercadolibre.desafio_java.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private String purchase1Json;
    private String purchase2Json;

    private String receipt1Json;
    private String receipt2Json;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();

        PurchaseRequestDTO purchaseRequestDTO1 = new PurchaseRequestDTO();
        purchaseRequestDTO1.setUserId(1L);
        purchaseRequestDTO1.addPurchaseDTO(new PurchaseDTO(1L, 1));
        purchaseRequestDTO1.addPurchaseDTO(new PurchaseDTO(2L, 1));

        this.purchase1Json = this.stringValue(purchaseRequestDTO1);

        ReceiptDTO receiptDTO1 = this.getReceiptFrom("src/test/data/receipt_1.json");

        Map<String, Object> receipt1 = new HashMap<>();
        receipt1.put("order", receiptDTO1);
        receipt1.put("status", "PENDING");
        this.receipt1Json = this.stringValue(receipt1);

        PurchaseRequestDTO purchaseRequestDTO2 = new PurchaseRequestDTO();
        purchaseRequestDTO2.setUserId(1L);
        purchaseRequestDTO2.addPurchaseDTO(new PurchaseDTO(20L, 1));
        purchaseRequestDTO2.addPurchaseDTO(new PurchaseDTO(2L, 1));

        this.purchase2Json = this.stringValue(purchaseRequestDTO2);
    }

    @Test
    void shouldReturnCorrectTotalPrice() throws Exception {
        this.mockMvc.perform(post("/api/v1/purchase_request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(purchase1Json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(receipt1Json));
    }

    @Test
    void shouldReturnNotFound() throws Exception {
        this.mockMvc.perform(post("/api/v1/purchase_request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(purchase2Json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private ReceiptDTO getReceiptFrom(String filePath) {
        File file = null;

        try {
            file = ResourceUtils.getFile(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TypeReference<ReceiptDTO> typeReference = new TypeReference<>() {};
        ReceiptDTO receiptDTO = null;

        try {
            receiptDTO = this.objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receiptDTO;
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
