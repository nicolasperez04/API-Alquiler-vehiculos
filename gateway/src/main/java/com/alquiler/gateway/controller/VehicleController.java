package com.alquiler.gateway.controller;

import com.alquiler.gateway.client.VehicleServiceClient;
import com.alquiler.gateway.dto.VehicleDTO;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleServiceClient vehicleServiceClient;
    @Hidden
    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody VehicleDTO vehicle) {
        return vehicleServiceClient.createVehicle(vehicle);
    }
    @Hidden
    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable Long id) {
        return vehicleServiceClient.getVehicleById(id);
    }
    @Hidden
    @PutMapping("/changePrice/{id}")
    public ResponseEntity<?> changePrice(@RequestBody VehicleDTO request, @PathVariable("id") Long id) {
        return vehicleServiceClient.changePrice(request, id);
    }
    @Hidden
    @GetMapping("/filter-type")
    public ResponseEntity<?> getByType(@RequestParam("type") String type) {
        return vehicleServiceClient.getByType(type);
    }
    @Hidden
    @GetMapping("/filter-status")
    public ResponseEntity<?> getByStatus(@RequestParam("status") String status) {
        return vehicleServiceClient.getByStatus(status);
    }
    @Hidden
    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id, @RequestBody VehicleDTO request) {
        return vehicleServiceClient.changeStatus(id, request);
    }
    @Hidden
    @GetMapping("/getVehicles")
    public ResponseEntity<?> getVehicles() {
        return vehicleServiceClient.getVehicles();
    }
    @Hidden
    @GetMapping("/getVehiclesReserved")
    public ResponseEntity<?> getVehiclesReservedByUser() {
        return vehicleServiceClient.getVehiclesReservedByUser();
    }


}
