package com.alquiler.booking_service.entity;

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
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String createdByUsername;

    private Long vehicleId;

    private Date startDate;

    private Date endDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private double totalPrice;
}
