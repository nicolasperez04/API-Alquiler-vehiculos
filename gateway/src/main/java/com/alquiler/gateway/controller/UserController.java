package com.alquiler.gateway.controller;

import com.alquiler.gateway.client.UserServiceClient;
import com.alquiler.gateway.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserServiceClient userServiceClient;


    @PutMapping("/update")
    public ResponseEntity<?> updateInfo(@RequestBody UserDTO user) {
        return userServiceClient.updateInfo(user);
    }
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        return userServiceClient.getUserInfo();
    }
    @GetMapping("/infoAllUsers")
    public ResponseEntity<?> getAllUsers() {
        return userServiceClient.getAllUsers();
    }

}
