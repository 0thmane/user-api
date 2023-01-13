package com.assessment.userapi.service;

import com.assessment.userapi.domain.User;
import com.assessment.userapi.dto.UserCreationDTO;
import com.assessment.userapi.dto.UserDTO;
import com.assessment.userapi.exception.UserNotFoundException;
import com.assessment.userapi.mapper.UserMapper;
import com.assessment.userapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     *  This method create a new user
     * @param userCreationDTO
     * @return User info
     */
    public UserDTO createUser(@Valid UserCreationDTO userCreationDTO){
        //the userCreationDTO is already validated by annotations
        User user = UserMapper.INSTANCE.userCreationDTOToUser(userCreationDTO);
        user = this.userRepository.save(user);
        //map user entity to user dto
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    /**
     * This method search a user by id, and return it if exists, otherwise we throw UserNotFoundException
     * @param id
     * @return User info
     */
    public UserDTO findUserById(Long id){
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return UserMapper.INSTANCE.userToUserDTO(user);
    }
}
