package com.alquiler.gateway.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {
    private Long id;

    private String createdByUsername;

    private Long vehicleId;

    private Date startDate;

    private Date endDate;

    private String status;

    private double totalPrice;
}
