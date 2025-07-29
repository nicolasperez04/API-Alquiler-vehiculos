package com.alquiler.User_Service.controller;

import com.alquiler.User_Service.dto.AuthRequest;


import com.alquiler.User_Service.dto.UserDTO;
import com.alquiler.User_Service.entity.User;
import com.alquiler.User_Service.repository.UserRepository;
import com.alquiler.User_Service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;



    @PutMapping("/update")
    public ResponseEntity<?> updateInfo(@RequestBody UserDTO user){
        return userService.updateInfo(user);

    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        return userService.getUserInfo();
    }

    @GetMapping("/infoAllUsers")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/role/{username}")
    public ResponseEntity<?> getRole(@PathVariable String username) {
        return userService.getRole(username);
    }

}
