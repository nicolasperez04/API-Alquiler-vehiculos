package com.alquiler.booking_service.Client;

import com.alquiler.booking_service.dto.vehicle.VehicleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "vehicle-service",
        path = "/vehicles"
       // url = "http://localhost:8082"
)
public interface VehicleFeignClient {

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id);
}
