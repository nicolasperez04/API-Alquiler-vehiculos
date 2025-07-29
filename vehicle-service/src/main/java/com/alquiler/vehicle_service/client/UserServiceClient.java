package com.alquiler.vehicle_service.client;


import com.alquiler.vehicle_service.dto.user.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(
        value = "user-service",
        path = "/users"
       // url = "http://localhost:8081"
)
public interface UserServiceClient {


    @GetMapping("/info")
    ResponseEntity<?> getUserInfo();

    @GetMapping("/role/{username}")
    Map<String, Role> getUserRole(@PathVariable String username);

}
