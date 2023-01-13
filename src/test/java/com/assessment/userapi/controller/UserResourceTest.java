package com.assessment.userapi.controller;

import com.assessment.userapi.domain.Gender;
import com.assessment.userapi.domain.ResidentialCountry;
import com.assessment.userapi.dto.UserCreationDTO;
import com.assessment.userapi.dto.UserDTO;
import com.assessment.userapi.exception.UserNotFoundException;
import com.assessment.userapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.Instant;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    void whenCreateUser_thenReturnUserInfo() throws Exception {
        UserCreationDTO userCreationDTO = generateUserCreationDTO();
        UserDTO expectedUserDTO = generateUserDTO();
        when(userService.createUser(userCreationDTO))
                .thenReturn(expectedUserDTO);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mockMvc.perform(
                        post("/api/users").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userCreationDTO)))
                .andExpect(status().isOk());


    }

    @Test
    void whenCreateUserWithoutBirthDate_thenReturnException() throws Exception {
        UserCreationDTO userCreationDTO = generateUserCreationDTOWithoutBirthDate();
        UserDTO expectedUserDTO = generateUserDTO();
        when(userService.createUser(userCreationDTO))
                .thenReturn(expectedUserDTO);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mockMvc.perform(
                        post("/api/users").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userCreationDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateUserWithoutUsername_thenReturnException() throws Exception {
        UserCreationDTO userCreationDTO = generateUserCreationDTOWithoutUsername();
        UserDTO expectedUserDTO = generateUserDTO();
        when(userService.createUser(userCreationDTO))
                .thenReturn(expectedUserDTO);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mockMvc.perform(
                        post("/api/users").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userCreationDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateUserWithoutResidentialCountry_thenReturnException() throws Exception {
        UserCreationDTO userCreationDTO = generateUserCreationDTOWithoutResidentialCountry();
        UserDTO expectedUserDTO = generateUserDTO();
        when(userService.createUser(userCreationDTO))
                .thenReturn(expectedUserDTO);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mockMvc.perform(
                        post("/api/users").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userCreationDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindById_thenReturnUserInfo() throws Exception {
        UserDTO expectedUserDTO = generateUserDTO();
        when(userService.findUserById(1l))
                .thenReturn(expectedUserDTO);

        mockMvc.perform(
                        get("/api/users/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenFindByIdNotFound_thenReturnException() throws Exception {
        when(userService.findUserById(1l))
                .thenThrow(new UserNotFoundException());

        mockMvc.perform(
                        get("/api/users/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

   // @Test
   // public void whenCreateUserWithoutUsername_thenReturnException()

    private UserCreationDTO generateUserCreationDTO(){
        return UserCreationDTO.builder()
                .birthDate(LocalDate.now().minusYears(20))
                .username("Username 1")
                .gender(Gender.FEMALE)
                .phone("0606060606")
                .residentialCountry(ResidentialCountry.FR)
                .build();
    }

    private UserCreationDTO generateUserCreationDTOWithoutBirthDate(){
        return UserCreationDTO.builder()
                .username("Username 1")
                .gender(Gender.FEMALE)
                .phone("0606060606")
                .residentialCountry(ResidentialCountry.FR)
                .build();
    }


    private UserDTO generateUserDTO(){
        return UserDTO.builder()
                .birthDate(LocalDate.now().minusYears(20))
                .username("Username 1")
                .gender(Gender.FEMALE)
                .phone("0606060606")
                .residentialCountry(ResidentialCountry.FR)
                .id(1l)
                .createdDate(Instant.now())
                .lastModifiedDate(Instant.now())
                .build();
    }

    private UserCreationDTO generateUserCreationDTOWithoutUsername(){
        return UserCreationDTO.builder()
                .birthDate(LocalDate.now().minusYears(20))
                .gender(Gender.FEMALE)
                .phone("0606060606")
                .residentialCountry(ResidentialCountry.FR)
                .build();
    }

    private UserCreationDTO generateUserCreationDTOWithoutResidentialCountry(){
        return UserCreationDTO.builder()
                .birthDate(LocalDate.now().minusYears(20))
                .username("Username 1")
                .gender(Gender.FEMALE)
                .phone("0606060606")
                .build();
    }
}