package com.alquiler.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    private String username;

    private String email;

    private String password;

    private String name;

    private String lastname;

    private Long phone;

    private String address;

    private String city;

    private Long dni;
}
