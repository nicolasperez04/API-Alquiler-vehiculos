package com.alquiler.User_Service.controller;

import com.alquiler.User_Service.dto.AuthRequest;


import com.alquiler.User_Service.dto.UserDTO;
import com.alquiler.User_Service.entity.User;
import com.alquiler.User_Service.repository.UserRepository;
import com.alquiler.User_Service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;


    @Operation(summary = "Update user information",
            description = "This endpoint allows a user to update their personal information such as name, email, etc."
    )

    @ApiResponse(
            responseCode = "200",
            description = "User information updated successfully",
            content =@Content(schema = @Schema(implementation = UserDTO.class)))

    @ApiResponse(responseCode = "400",
            description = "Invalid input data or user not found",
            content = @Content)

     @ApiResponse(responseCode = "403",
            description = "Access denied for non-authenticated users",
            content = @Content)


    @PutMapping("/update")
    public ResponseEntity<?> updateInfo(@RequestBody UserDTO user){
        return userService.updateInfo(user);

    }

    @Operation(summary = "Get user information", description = "This endpoint retrieves the information of the currently authenticated user.")

    @ApiResponse(
            responseCode = "200",
            description = "User information retrieved successfully",
            content = @Content(schema = @Schema(implementation = User.class))
    )

    @ApiResponse(responseCode = "401",
            description = "User not authenticated or not found",
            content = @Content)

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        return userService.getUserInfo();
    }

    @Operation(summary = "Get all users", description = "This endpoint retrieves the information of all registered users.Only users with the admin role can access")

    @ApiResponse(
            responseCode = "200",
            description = "All users retrieved successfully",
            content = @Content(schema = @Schema(implementation = User.class))
    )

    @ApiResponse(responseCode = "403",
            description = "Access denied for non-admin users",
            content = @Content)

    @GetMapping("/infoAllUsers")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user role", description = "This endpoint retrieves the role of a user by their username.")

    @ApiResponse(
            responseCode = "200",
            description = "User role retrieved successfully",
            content = @Content(schema = @Schema(implementation = User.class))
    )

    @ApiResponse(responseCode = "404",
            description = "User not found",
            content = @Content)
    @GetMapping("/role/{username}")
    public ResponseEntity<?> getRole(@PathVariable String username) {
        return userService.getRole(username);
    }

}
