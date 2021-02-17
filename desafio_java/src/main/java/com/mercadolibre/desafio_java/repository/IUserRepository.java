package com.mercadolibre.desafio_java.repository;

import com.mercadolibre.desafio_java.model.dto.response.UserDTO;

import java.util.List;

public interface IUserRepository {
    List<UserDTO> findAll();
    UserDTO findById(Long id);
    void save(UserDTO userDTO);
    Long nextId();
    List<UserDTO> loadJsonFile();
}
