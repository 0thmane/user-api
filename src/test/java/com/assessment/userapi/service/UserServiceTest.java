package com.assessment.userapi.service;

import com.assessment.userapi.domain.Gender;
import com.assessment.userapi.domain.ResidentialCountry;
import com.assessment.userapi.domain.User;
import com.assessment.userapi.dto.UserCreationDTO;
import com.assessment.userapi.dto.UserDTO;
import com.assessment.userapi.exception.UserNotFoundException;
import com.assessment.userapi.mapper.UserMapper;
import com.assessment.userapi.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Autowired
    public UserService userService;

    @MockBean
    public UserRepository userRepository;

    @Test
    public void whenCreateUserWithValidData_thenReturnInfoUser(){
        User expectedUser = generateUser();
        when(userRepository.save(any())).thenReturn(expectedUser);

        UserCreationDTO userCreationDTO = generateUserCreationDTO();
        UserDTO userDTO = this.userService.createUser(userCreationDTO);
        assertThat(userDTO).isNotNull();
        verifyUserDTO(userDTO, UserMapper.INSTANCE.userToUserDTO(expectedUser));
    }

    @Test
    public void whenCreateUserWithoutResidentialCode_thenReturnException(){
        User expectedUser = generateUser();
        when(userRepository.save(any())).thenReturn(expectedUser);

        UserCreationDTO userCreationDTO = generateUserCreationDTOWithoutResidentialCountry();
        assertThatThrownBy(() -> this.userService.createUser(userCreationDTO))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Invalid residentialCountry : residentialCountry is null");
    }

    @Test
    public void whenCreateUserWithoutUsername_thenReturnException(){
        User expectedUser = generateUser();
        when(userRepository.save(any())).thenReturn(expectedUser);

        UserCreationDTO userCreationDTO = generateUserCreationDTOWithoutUsername();
        assertThatThrownBy(() -> this.userService.createUser(userCreationDTO))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Invalid username : username is null");
    }

    @Test
    public void whenCreateUserWithoutBirthDate_thenReturnException(){
        User expectedUser = generateUser();
        when(userRepository.save(any())).thenReturn(expectedUser);

        UserCreationDTO userCreationDTO = generateUserCreationDTOWithoutBirthDate();
        assertThatThrownBy(() -> this.userService.createUser(userCreationDTO))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Invalid birthDate : birthDate is null");
    }

    @Test
    public void whenCreateUserNotAdult_thenReturnException(){
        User expectedUser = generateUser();
        when(userRepository.save(any())).thenReturn(expectedUser);

        UserCreationDTO userCreationDTO = generateUserCreationDTONotAdult();
        assertThatThrownBy(() -> this.userService.createUser(userCreationDTO))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Invalid birthDate : Only adult are allowed to create an account");
    }

    @Test
    public void whenFindById_thenReturnUserInfo(){
        User expectedUser = generateUser();
        when(userRepository.findById(1l)).thenReturn(Optional.of(expectedUser));

        UserDTO newUser = this.userService.findUserById(1l);
        assertThat(newUser).isNotNull();
        verifyUserDTO(newUser, UserMapper.INSTANCE.userToUserDTO(expectedUser));
        assertThat(newUser.getId()).isEqualTo(expectedUser.getId());
    }

    @Test
    public void whenFindByIdNotFound_thenReturnException(){
        when(userRepository.findById(1l)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> this.userService.findUserById(1l))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    private void verifyUserDTO(UserDTO userDTO, UserDTO expectedUserDTO){
        assertThat(expectedUserDTO.getUsername()).isEqualTo(userDTO.getUsername());
        assertThat(expectedUserDTO.getGender()).isEqualTo(userDTO.getGender());
        assertThat(expectedUserDTO.getBirthDate()).isEqualTo(userDTO.getBirthDate());
        assertThat(expectedUserDTO.getPhone()).isEqualTo(userDTO.getPhone());
        assertThat(expectedUserDTO.getResidentialCountry()).isEqualTo(userDTO.getResidentialCountry());
        assertThat(expectedUserDTO.getCreatedDate()).isNotNull();
        assertThat(expectedUserDTO.getLastModifiedDate()).isNotNull();
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

    private UserCreationDTO generateUserCreationDTONotAdult(){
        return UserCreationDTO.builder()
                .birthDate(LocalDate.now())
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

    private User generateUser(){
        return User.builder()
                .birthDate(LocalDate.now().minusYears(20))
                .username("Username 1")
                .gender(Gender.FEMALE)
                .phone("0606060606")
                .residentialCountry(ResidentialCountry.FR)
                .id(1l)
                .build();
    }
}
