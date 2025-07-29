package com.alquiler.vehicle_service.service;

import com.alquiler.vehicle_service.client.UserServiceClient;
import com.alquiler.vehicle_service.dto.booking.Booking;
import com.alquiler.vehicle_service.dto.user.Role;
import com.alquiler.vehicle_service.entity.Status;
import com.alquiler.vehicle_service.entity.Type;
import com.alquiler.vehicle_service.entity.Vehicle;
import com.alquiler.vehicle_service.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleService {

    private VehicleRepository vehicleRepository;

    private UserServiceClient userServiceClient;

    public ResponseEntity<?> createVehicle(Vehicle vehicleRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Role userRole = userServiceClient.getUserRole(username).get("role");
            if (Role.SELLER.equals(userRole)) {
                vehicleRequest.setCreatedByUsername(username);
                vehicleRepository.save(vehicleRequest);
                return ResponseEntity.ok(vehicleRequest);
            } else {
                return ResponseEntity.status(403).body("You do not have permission to create a vehicle");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("User role verification failed: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getVehicleById(Long id) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        return ResponseEntity.ok(vehicle);
    }


    public ResponseEntity<?> updateprice(Long id, Vehicle request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (!vehicle.getCreatedByUsername().equals(username)) {
            return ResponseEntity.status(403).body("You do not have permission to update this vehicle");
        }


        vehicle.setPricePerDay(request.getPricePerDay());
        vehicleRepository.save(vehicle);

        return ResponseEntity.ok(vehicle);
    }


    public List<Vehicle> filterByType(Type type) {
        if (type != null){
            return vehicleRepository.findByType(type);
        } else {
            return vehicleRepository.findAll();
        }

    }

    public List<Vehicle> filterByStatus (Status status){
        if (status != null){
            return vehicleRepository.findByStatus(status);
        } else {
            return vehicleRepository.findAll();
        }
    }

    public ResponseEntity<?> updateStatus(Long id, Vehicle request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(id);
        if (vehicleOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Vehicle not found");
        }
        Vehicle vehicle = vehicleOptional.get();


        if (!vehicle.getCreatedByUsername().equals(username)) {
            return ResponseEntity.status(403).body("You do not have permission to update this vehicle");
        }

        Status currentStatus = vehicle.getStatus();
        if (currentStatus == Status.RESERVED) {
            return ResponseEntity.status(400).body("Cannot change status from RESERVED to " + request.getStatus());
        }

        vehicle.setStatus(request.getStatus());
        vehicleRepository.save(vehicle);

        String message = "Vehicle status updated to " + request.getStatus();
        return ResponseEntity.ok(Map.of("message", message));

    }

    @KafkaListener(topics = "booked-vehicle-topic", groupId = "vehicle-group", containerFactory = "bookingKafkaListenerFactory")
    public void changeStatusBookedVehicle(Booking booking){

        Vehicle vehicle = vehicleRepository.findById(booking.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setStatus(Status.RESERVED);
        vehicleRepository.save(vehicle);


    }
    @KafkaListener(topics = "cancelled-vehicle-topic", groupId = "vehicle-group", containerFactory = "bookingKafkaListenerFactory")
    public void changeStatusCancelledVehicle(Booking booking){

        Vehicle vehicle = vehicleRepository.findById(booking.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setStatus(Status.AVAILABLE);
        vehicleRepository.save(vehicle);
    }


    public ResponseEntity<?> getVehicles() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Vehicle> vehicles = vehicleRepository.findByCreatedByUsername(username);
        if (vehicles.isEmpty()) {
            return ResponseEntity.status(404).body("No vehicles found for this user");
        }
        return ResponseEntity.ok(vehicles);
    }

    public ResponseEntity<?> getVehiclesReservedByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Vehicle> vehicles = vehicleRepository.findByStatus(Status.RESERVED)
                .stream()
                .filter(vehicle -> vehicle.getCreatedByUsername().equals(username))
                .toList();
        if (vehicles.isEmpty()) {
            return ResponseEntity.status(404).body("No reserved vehicles found for this user");
        }
        return ResponseEntity.ok(vehicles);
    }


}
