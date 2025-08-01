package com.alquiler.gateway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RegisterDTO {
    @Schema(description = "Unique username for the user", example = "john_doe")
    private String username;

@Schema(description = "Email address of the user", example = "user@gmail.com")
    private String email;
@Schema(description = "Password for the user account", example = "password123")
    private String password;
@Schema(description = "First name of the user", example = "John")
    private String name;
@Schema(description = "Last name of the user", example = "Doe")
    private String lastname;
@Schema(description = "Phone number of the user", example = "1234567890")
    private Long phone;
@Schema(description = "Address of the user", example = "123 Main St")
    private String address;
@Schema(description = "City where the user resides", example = "New York")
    private String city;
@Schema(description = "DNI (National Identity Document) number of the user", example = "12345678")
    private Long dni;
}
