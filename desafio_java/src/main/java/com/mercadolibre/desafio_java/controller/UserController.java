package com.mercadolibre.desafio_java.controller;

import com.mercadolibre.desafio_java.model.dto.request.UserSearchDTO;
import com.mercadolibre.desafio_java.model.dto.response.UserDTO;
import com.mercadolibre.desafio_java.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    @Autowired
    IUserService iUserService;

    @PostMapping(value = "")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
        iUserService.addUser(userDTO);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserDTO>> getUsers(UserSearchDTO userSearchDTO) {
        return new ResponseEntity<>(iUserService.findUsers(userSearchDTO), HttpStatus.OK);
    }
}
