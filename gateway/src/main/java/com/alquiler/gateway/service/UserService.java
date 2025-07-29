package com.alquiler.gateway.service;

import com.alquiler.gateway.client.UserServiceClient;
import com.alquiler.gateway.dto.RegisterDTO;
import com.alquiler.gateway.dto.ResponseToken;
import com.alquiler.gateway.dto.SendUserService;
import com.alquiler.gateway.entity.User;
import com.alquiler.gateway.kafkaConfig.KafkaTopic;
import com.alquiler.gateway.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private KafkaTopic kafkaTopic;

    public ResponseEntity<?> register(RegisterDTO request) {

        if (userRepository.existsByUsername(request.getUsername())){
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body("Email already exists");
        }

        if (request.getUsername() == null || request.getEmail() == null || request.getPassword() == null
        || request.getUsername().isEmpty() || request.getEmail().isEmpty() || request.getPassword().isEmpty()
        || request.getDni() == null || request.getName() == null || request.getLastname() == null
        || request.getPhone() == null || request.getAddress() == null || request.getCity() == null
        || request.getName().isEmpty() || request.getLastname().isEmpty()|| request.getAddress().isEmpty()
        || request.getCity().isEmpty()) {
            return ResponseEntity.badRequest().body("All fields are required");
        }


        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        kafkaTopic.sendDataUser(request);
        String token = jwtService.generateToken(user);
        ResponseToken response = new ResponseToken();
        response.setToken(token);

        return ResponseEntity.ok(response);

    }


    public ResponseEntity<?> login(RegisterDTO request){

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();

            String token = jwtService.generateToken(user);

            return ResponseEntity.ok(new ResponseToken(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

    }

}
