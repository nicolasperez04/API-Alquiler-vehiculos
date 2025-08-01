package com.alquiler.gateway.controller;

import com.alquiler.gateway.dto.RegisterDTO;
import com.alquiler.gateway.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    @Operation(summary = "Register a new user",
            description = "This endpoint allows a new user to register.")
    @ApiResponse(
            responseCode = "200",
            description = "User registered successfully",
            content = @Content(schema = @Schema(implementation = RegisterDTO.class))
    )
    @ApiResponse(responseCode = "400",
            description = "Invalid input data or user already exists",
            content = @Content)
    @PostMapping("/register")
    public ResponseEntity<?> register(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User registration details",
            required = true,
            content = @Content(schema = @Schema(implementation = RegisterDTO.class))
    ) @RequestBody RegisterDTO user) {
        return userService.register(user);
    }


    @Operation(summary = "User login",
            description = "This endpoint allows a user to log in with their credentials. Just use your username and password.")

    @ApiResponse(
            responseCode = "200",
            description = "User logged in successfully",
            content = @Content(schema = @Schema(implementation = RegisterDTO.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Invalid username or password",
            content = @Content
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User login credentials",
            required = true,
            content = @Content(schema = @Schema(implementation = RegisterDTO.class),
                    examples = @ExampleObject(
                            name = "LoginExample",
                            summary = "Login request",
                            value = "{ \"username\": \"john_doe\", \"password\": \"123456\" }"))
    ) @RequestBody RegisterDTO user) {
        return userService.login(user);
    }


}
