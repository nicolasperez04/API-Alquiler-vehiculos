package com.alquiler.vehicle_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String createdByUsername;

    @Schema(description = "Brand", example = "Mazda")
    @NotBlank(message = "Brand is mandatory")
    private String brand;

    @Schema(description = "Model", example = "CX-5")
    @NotBlank(message = "Model is mandatory")
    private String model;

    @Schema(description = "Color", example = "Red")
    @NotNull(message = "Year is mandatory")
    private Long year;

    @Schema(description = "Type", example = "CAR")
    @NotNull(message = "Type is mandatory")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Schema(description = "Status", example = "AVAILABLE")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Schema(description = "Price per day", example = "50.0")
    @NotNull(message = "Price per day is mandatory")
    private Double pricePerDay;

}
