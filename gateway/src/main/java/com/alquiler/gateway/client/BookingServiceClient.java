package com.alquiler.gateway.client;

import com.alquiler.gateway.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        value = "booking-service",
        path = "/bookings"
        //url = "http://localhost:8083"
)
public interface BookingServiceClient {

    @PostMapping
    ResponseEntity<?> createBooking(@RequestBody BookingDTO booking);

    @GetMapping("/pending")
     ResponseEntity<?> pendingBookings();

    @PostMapping("/confirm/{bookingId}")
     ResponseEntity<?> confirmBooking(@PathVariable Long bookingId);

    @GetMapping("/getBookingsByUser")
    ResponseEntity<?> getBookingsByUser();

    @PutMapping("/cancel/{bookingId}")
    String cancelBooking(@PathVariable Long bookingId);

}
