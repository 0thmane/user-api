package com.assessment.userapi;

import com.assessment.userapi.domain.Gender;
import com.assessment.userapi.domain.ResidentialCountry;
import com.assessment.userapi.domain.User;
import com.assessment.userapi.dto.UserCreationDTO;
import com.assessment.userapi.dto.UserDTO;
import com.assessment.userapi.exception.UserNotFoundException;
import com.assessment.userapi.mapper.UserMapper;
import com.assessment.userapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenCreateUser_thenReturnUserInfo() throws Exception {
        UserCreationDTO userCreationDTO = generateUserCreationDTO();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mockMvc.perform(
                        post("/api/users").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userCreationDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void whenCreateUserWithoutBirthDate_thenReturnException() throws Exception {
        UserCreationDTO userCreationDTO = generateUserCreationDTOWithoutBirthDate();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mockMvc.perform(
                        post("/api/users").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userCreationDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateUserWithoutUsername_thenReturnException() throws Exception {
        UserCreationDTO userCreationDTO = generateUserCreationDTOWithoutUsername();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mockMvc.perform(
                        post("/api/users").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userCreationDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateUserWithoutResidentialCountry_thenReturnException() throws Exception {
        UserCreationDTO userCreationDTO = generateUserCreationDTOWithoutResidentialCountry();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mockMvc.perform(
                        post("/api/users").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userCreationDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindById_thenReturnUserInfo() throws Exception {
        User user = persistNewUser();
        mockMvc.perform(
                        get("/api/users/{id}", user.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenFindByIdNotFound_thenReturnException() throws Exception {
        mockMvc.perform(
                        get("/api/users/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


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

    private User persistNewUser(){
        UserCreationDTO userCreationDTO = generateUserCreationDTO();
        return this.userRepository.save(UserMapper.INSTANCE.userCreationDTOToUser(userCreationDTO));
    }

}
