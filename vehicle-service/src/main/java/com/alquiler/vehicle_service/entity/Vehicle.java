package com.alquiler.vehicle_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String createdByUsername;

    @NotBlank(message = "Brand is mandatory")
    private String brand;

    @NotBlank(message = "Model is mandatory")
    private String model;

    @NotNull(message = "Year is mandatory")
    private Long year;

    @NotNull(message = "Mileage is mandatory")
    @Enumerated(EnumType.STRING)
    private Type type;


    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Price per day is mandatory")
    private Double pricePerDay;

}
