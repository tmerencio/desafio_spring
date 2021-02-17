package com.mercadolibre.desafio_java.util.filter;

import com.mercadolibre.desafio_java.model.dto.request.UserSearchDTO;
import com.mercadolibre.desafio_java.model.dto.response.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UserFilter {
    public static Predicate<UserDTO> getInstance(UserSearchDTO userSearchDTO) {
        return getFilters(userSearchDTO).stream().reduce(u -> true, Predicate::and);
    }

    public static List<Predicate<UserDTO>> getFilters(UserSearchDTO userSearchDTO) {
        List<Predicate<UserDTO>> filters = new ArrayList<>();

        if (userSearchDTO.getProvince() != null) {
            filters.add(userDTO -> userDTO.getProvince().equals(userSearchDTO.getProvince()));
        }

        return filters;
    }
}
