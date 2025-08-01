package com.alquiler.gateway.controller;

import com.alquiler.gateway.client.UserServiceClient;
import com.alquiler.gateway.dto.UserDTO;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserServiceClient userServiceClient;

    @Hidden
    @PutMapping("/update")
    public ResponseEntity<?> updateInfo(@RequestBody UserDTO user) {
        return userServiceClient.updateInfo(user);
    }
    @Hidden
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        return userServiceClient.getUserInfo();
    }
    @Hidden
    @GetMapping("/infoAllUsers")
    public ResponseEntity<?> getAllUsers() {
        return userServiceClient.getAllUsers();
    }

}
