package com.mercadolibre.desafio_java.service.impl;

import com.mercadolibre.desafio_java.model.dto.request.UserSearchDTO;
import com.mercadolibre.desafio_java.model.dto.response.UserDTO;
import com.mercadolibre.desafio_java.repository.IUserRepository;
import com.mercadolibre.desafio_java.service.IUserService;
import com.mercadolibre.desafio_java.util.filter.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository iUserRepository;

    @Override
    public List<UserDTO> findUsers(UserSearchDTO userSearchDTO) {
        return iUserRepository
                .findAll()
                .stream()
                .filter(UserFilter.getInstance(userSearchDTO))
                .collect(Collectors.toList());
    }

    @Override
    public void addUser(UserDTO userDTO) {
        iUserRepository.save(userDTO);
    }
}
