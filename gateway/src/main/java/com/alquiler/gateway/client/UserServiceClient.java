package com.alquiler.gateway.client;

import com.alquiler.gateway.dto.SendUserService;
import com.alquiler.gateway.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "user-service",
        path = "/users"
       // url = "http://localhost:8081"
)
public interface UserServiceClient {


    @PutMapping("/update")
    ResponseEntity<?> updateInfo(@RequestBody UserDTO user);

    @GetMapping("/info")
    ResponseEntity<?> getUserInfo();

    @GetMapping("/infoAllUsers")
    ResponseEntity<?> getAllUsers();
}
