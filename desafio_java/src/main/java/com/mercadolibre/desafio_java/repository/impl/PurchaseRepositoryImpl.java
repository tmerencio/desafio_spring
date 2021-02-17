package com.mercadolibre.desafio_java.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_java.exception.NoDataFoundException;
import com.mercadolibre.desafio_java.model.dto.response.ReceiptDTO;
import com.mercadolibre.desafio_java.repository.IPurchaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Repository
public class PurchaseRepositoryImpl implements IPurchaseRepository {
    private final String FILE_PATH = "src/main/resources/data/purchases.json";

    @Override
    public List<ReceiptDTO> findAll() {
        List<ReceiptDTO> receiptDTOList = this.loadJsonFile();

        if (receiptDTOList.isEmpty()) {
            throw new NoDataFoundException();
        }

        return receiptDTOList;
    }

    @Override
    public void save(ReceiptDTO receiptDTO) {
        List<ReceiptDTO> receiptDTOList = this.loadJsonFile();

        receiptDTOList.add(receiptDTO);

        this.updateJsonFile(receiptDTOList);
    }

    @Override
    public Long nextId() {
        return (long) this.loadJsonFile().size();
    }

    private List<ReceiptDTO> loadJsonFile() {
        File file = null;

        try {
            file = ResourceUtils.getFile(FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        TypeReference<List<ReceiptDTO>> typeReference = new TypeReference<>() {};
        List<ReceiptDTO> receiptDTOList = null;

        try {
            receiptDTOList = objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receiptDTOList;
    }

    private void updateJsonFile(List<ReceiptDTO> userDTOList) {
        File file = null;

        try {
            file = ResourceUtils.getFile(FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(file, userDTOList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
