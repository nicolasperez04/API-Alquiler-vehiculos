package com.alquiler.User_Service.service;

import com.alquiler.User_Service.dto.AuthRequest;
import com.alquiler.User_Service.dto.UserDTO;
import com.alquiler.User_Service.dto.gateway.RegisterDTO;
import com.alquiler.User_Service.entity.Role;
import com.alquiler.User_Service.entity.User;
import com.alquiler.User_Service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;



    @KafkaListener(topics = "data-user-topic", groupId = "user-group", containerFactory = "productKafkaListenerFactory")
    public void createUser (RegisterDTO request){

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .lastname(request.getLastname())
                .phone(request.getPhone())
                .address(request.getAddress())
                .city(request.getCity())
                .dni(request.getDni())
                .role(Role.USER)
                .build();

        userRepository.save(user);

    }

    public ResponseEntity<?> updateInfo(UserDTO user){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getName() != null)existingUser.setName(user.getName());
       if (user.getLastname() != null) existingUser.setLastname(user.getLastname());
        if (user.getPhone() != null) existingUser.setPhone(user.getPhone());
        if (user.getAddress() != null) existingUser.setAddress(user.getAddress());
        if (user.getCity() != null) existingUser.setCity(user.getCity());
        if (user.getDni() != null) existingUser.setDni(user.getDni());

        UserDTO userDTO = UserToUserDTO(existingUser);

        userRepository.save(existingUser);
        return ResponseEntity.ok(userDTO);
    }



    public ResponseEntity<?> getUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDTO userDTO = UserToUserDTO(user);

        return ResponseEntity.ok(userDTO);
    }

    public ResponseEntity<?> getAllUsers() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(403).body("Access denied: Only admins can view all users");
        }

        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<Map<String, Role>> getRole(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user
                .map(u -> ResponseEntity.ok(Map.of("role", u.getRole())))
                .orElse(ResponseEntity.notFound().build());

    }


    private static UserDTO UserToUserDTO(User existingUser) {
        UserDTO userDTO = UserDTO.builder()
                .name(existingUser.getName())
                .lastname(existingUser.getLastname())
                .phone(existingUser.getPhone())
                .address(existingUser.getAddress())
                .city(existingUser.getCity())
                .dni(existingUser.getDni())
                .build();
        return userDTO;
    }



}
