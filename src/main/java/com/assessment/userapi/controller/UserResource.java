package com.assessment.userapi.controller;

import com.assessment.userapi.dto.UserCreationDTO;
import com.assessment.userapi.dto.UserDTO;
import com.assessment.userapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreationDTO userCreationDTO){
        return ResponseEntity.ok(this.userService.createUser(userCreationDTO));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.userService.findUserById(id));
    }

}
