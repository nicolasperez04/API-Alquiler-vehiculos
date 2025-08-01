package com.alquiler.User_Service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class UserDTO {
    @Schema(description = "Unique username for the user", example = "john_doe")
    private String name;
    @Schema(description = "Email address of the user", example = "john@gmail.com")
    private String lastname;
    @Schema(description = "Password for the user account", example = "password123")
    private Long phone;
    @Schema(description = "First name of the user", example = "John")
    private String address;
    @Schema(description = "Last name of the user", example = "Doe")
    private String city;
    @Schema(description = "Phone number of the user", example = "1234567890")
    private Long dni;
}
