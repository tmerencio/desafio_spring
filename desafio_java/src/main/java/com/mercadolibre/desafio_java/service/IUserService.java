package com.mercadolibre.desafio_java.service;

import com.mercadolibre.desafio_java.model.dto.request.UserSearchDTO;
import com.mercadolibre.desafio_java.model.dto.response.UserDTO;

import java.util.List;

public interface IUserService {
    List<UserDTO> findUsers(UserSearchDTO userSearchDTO);
    void addUser(UserDTO userDTO);
}
