package com.alquiler.booking_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Schema
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String createdByUsername;

    @Schema(description = "ID of the user who created the booking", example = "1")
    private Long vehicleId;

    @Schema(description = "reservation date", example = "2025-07-01")
    private Date startDate;

    @Schema(description = "reservation end date", example = "2025-07-10")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Schema(description = "Total price for the booking", example = "150.00")
    private double totalPrice;
}
