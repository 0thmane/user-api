package com.assessment.userapi.repository;

import com.assessment.userapi.domain.Gender;
import com.assessment.userapi.domain.ResidentialCountry;
import com.assessment.userapi.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    public UserRepository userRepository;

    @Test
    public void whenCreateUser_thenReturnUserInfo(){
        User newUser = createUser();
        User savedUser = this.userRepository.save(newUser);
        assertThat(savedUser).isNotNull();
        verifyUser(newUser, savedUser);
    }

    @Test
    public void whenFindById_thenReturnUserInfo(){
        //create a new user
        User newUser = createUser();
        this.userRepository.save(newUser);

        Optional<User> optionalUser = this.userRepository.findById(newUser.getId());
        assertThat(optionalUser).isPresent();
    }

    @Test
    public void whenFindByNotFoundId_thenReturnNull(){
        Optional<User> optionalUser = this.userRepository.findById(500l);
        assertThat(optionalUser).isEmpty();
    }

    private static User createUser(){
        return User.builder()
                .birthDate(LocalDate.now().minusYears(20))
                .username("Username 1")
                .gender(Gender.FEMALE)
                .phone("0606060606")
                .residentialCountry(ResidentialCountry.FR)
                .build();
    }

    private void verifyUser(User newUser, User expectedUser){
        assertThat(expectedUser.getUsername()).isEqualTo(newUser.getUsername());
        assertThat(expectedUser.getGender()).isEqualTo(newUser.getGender());
        assertThat(expectedUser.getBirthDate()).isEqualTo(newUser.getBirthDate());
        assertThat(expectedUser.getPhone()).isEqualTo(newUser.getPhone());
        assertThat(expectedUser.getResidentialCountry()).isEqualTo(newUser.getResidentialCountry());
        assertThat(expectedUser.getCreatedDate()).isNotNull();
        assertThat(expectedUser.getLastModifiedDate()).isNotNull();
    }
}
