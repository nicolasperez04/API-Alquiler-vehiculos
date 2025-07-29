package com.alquiler.booking_service.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

    private Long id;
    private String createdByUsername;
    private String brand;
    private String model;
    private Long year;
    private String type;
    private String status;
    private Double pricePerDay;
}
