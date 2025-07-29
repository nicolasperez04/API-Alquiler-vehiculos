package com.alquiler.vehicle_service.dto.booking;

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
public class Booking {


    private Long id;

    private String createdByUsername;

    private Long vehicleId;

    private Date startDate;

    private Date endDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private double totalPrice;
}
