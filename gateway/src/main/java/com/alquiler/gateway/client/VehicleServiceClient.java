package com.alquiler.gateway.client;

import com.alquiler.gateway.dto.VehicleDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        value = "vehicle-service",
        path = "/vehicles"
       // url = "http://localhost:8082"
)
public interface VehicleServiceClient {

    @PostMapping
    public ResponseEntity<?> createVehicle(@Valid @RequestBody VehicleDTO vehicle);

    @GetMapping("/{id}")
    ResponseEntity<?> getVehicleById(@PathVariable Long id);

    @PutMapping("/changePrice/{id}")
    ResponseEntity<?> changePrice(@RequestBody VehicleDTO request,@PathVariable Long id);

    @GetMapping("/filter-type")
    ResponseEntity<?> getByType(@RequestParam("type") String type);

    @GetMapping("/filter-status")
    ResponseEntity<?> getByStatus(@RequestParam("status") String status);

    @PutMapping("/changeStatus/{id}")
    ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestBody VehicleDTO request);

    @GetMapping("/getVehicles")
    ResponseEntity<?> getVehicles();

    @GetMapping("/getVehiclesReserved")
    ResponseEntity<?> getVehiclesReservedByUser();


}
