package com.mercadolibre.desafio_java;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_java.model.dto.response.UserDTO;
import com.mercadolibre.desafio_java.repository.IUserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    IUserRepository iUserRepository;

    private String newUserJson;
    private String listWithNewUserJson;
    private String badUserJson;
    private final String FILE_PATH = "src/main/resources/data/users.json";


    @BeforeAll
    public void init() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Vaciar archivo
        File file = null;

        try {
            file = ResourceUtils.getFile(FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            objectMapper.writeValue(file, new ArrayList<>());
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(iUserRepository.nextId());
        userDTO.setFirstName("New");
        userDTO.setLastName("User");
        userDTO.setUsername("newuser");
        userDTO.setEmail("new.user@mail.com");
        userDTO.setProvince("Buenos Aires");

        UserDTO badUser = new UserDTO();
        userDTO.setUsername("testuser");

        List<UserDTO> listWithNewUser = iUserRepository.loadJsonFile();
        listWithNewUser.add(userDTO);

        try {
            newUserJson = objectMapper.writeValueAsString(userDTO);
            listWithNewUserJson = objectMapper.writeValueAsString(listWithNewUser);
            badUserJson = objectMapper.writeValueAsString(badUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void addUserReturns200CodeTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void addSameUserReturnsConflictTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    void getUsersReturnsListTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(listWithNewUserJson));
    }

    @Test
    @Order(4)
    void notAddingAllParametersReturnsBadRequestTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(badUserJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
