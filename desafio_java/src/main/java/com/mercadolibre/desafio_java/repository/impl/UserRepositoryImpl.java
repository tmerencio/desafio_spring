package com.mercadolibre.desafio_java.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_java.exception.NoDataFoundException;
import com.mercadolibre.desafio_java.exception.UserAlredyExistsException;
import com.mercadolibre.desafio_java.exception.UserNotFoundException;
import com.mercadolibre.desafio_java.model.dto.response.UserDTO;
import com.mercadolibre.desafio_java.repository.IUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final String FILE_PATH = "src/main/resources/data/users.json";

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> userDTOList = this.loadJsonFile();

        if (userDTOList.isEmpty()) {
            throw new NoDataFoundException();
        }

        return userDTOList;
    }

    @Override
    public UserDTO findById(Long id) {
        return this.findAll()
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void save(UserDTO userDTO) {
        if(userIsNotValid(userDTO)) {
            throw new UserAlredyExistsException();
        }

        List<UserDTO> userDTOList = this.loadJsonFile();

        userDTO.setId(this.nextId());

        userDTOList.add(userDTO);

        this.updateJsonFile(userDTOList);
    }

    private boolean userIsNotValid(UserDTO userDTO) {
        return this.loadJsonFile().stream().anyMatch(u ->
                u.getEmail().equals(userDTO.getEmail()) || u.getUsername().equals(userDTO.getUsername())
        );
    }

    @Override
    public Long nextId() {
        return (long) this.loadJsonFile().size();
    }

    @Override
    public List<UserDTO> loadJsonFile() {
        File file = null;

        try {
            file = ResourceUtils.getFile(FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        TypeReference<List<UserDTO>> typeReference = new TypeReference<>() {};
        List<UserDTO> userDTOList = null;

        try {
            userDTOList = objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userDTOList;
    }

    private void updateJsonFile(List<UserDTO> userDTOList) {
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
