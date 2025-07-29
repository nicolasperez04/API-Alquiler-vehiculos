package com.alquiler.vehicle_service.Controller;

import com.alquiler.vehicle_service.entity.Status;
import com.alquiler.vehicle_service.entity.Type;
import com.alquiler.vehicle_service.entity.Vehicle;
import com.alquiler.vehicle_service.service.VehicleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@AllArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<?> createVehicle(@Valid @RequestBody Vehicle vehicle) {
        return vehicleService.createVehicle(vehicle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id);
    }


    @PutMapping("/changePrice/{id}")
    public ResponseEntity<?> changePrice(@PathVariable Long id, @RequestBody Vehicle request) {
        return vehicleService.updateprice(id, request);
    }

    @GetMapping("/filter-type")
    public ResponseEntity<List<Vehicle>> getByType(@RequestParam Type type) {
        return ResponseEntity.ok(vehicleService.filterByType(type));
    }

    @GetMapping("/filter-status")
    public ResponseEntity<List<Vehicle>> getByStatus(@RequestParam Status status) {
        return ResponseEntity.ok(vehicleService.filterByStatus(status));
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestBody Vehicle request) {
        return vehicleService.updateStatus(id, request);
    }

   @GetMapping("/getVehicles")
    public ResponseEntity<?> getVehicles() {
        return vehicleService.getVehicles();
    }

    @GetMapping("/getVehiclesReserved")
    public ResponseEntity<?> getVehiclesReservedByUser(){
        return vehicleService.getVehiclesReservedByUser();
    }



}
