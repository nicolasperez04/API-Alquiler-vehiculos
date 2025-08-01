package com.alquiler.gateway.controller;

import com.alquiler.gateway.client.BookingServiceClient;
import com.alquiler.gateway.dto.BookingDTO;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private BookingServiceClient bookingServiceClient;

    @Hidden
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO booking){
        return bookingServiceClient.createBooking(booking);
    }
    @Hidden
    @GetMapping("/pending")
    public ResponseEntity<?> pendingBookings() {
        return bookingServiceClient.pendingBookings();
    }
    @Hidden
    @PostMapping("/confirm/{bookingId}")
    public ResponseEntity<?> confirmBooking(@PathVariable Long bookingId) {
        return bookingServiceClient.confirmBooking(bookingId);
    }
    @Hidden
    @GetMapping("/getBookingsByUser")
    public ResponseEntity<?> getBookingsByUser() {
        return bookingServiceClient.getBookingsByUser();
    }
    @Hidden
    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        String response = bookingServiceClient.cancelBooking(bookingId);
        return ResponseEntity.ok(response);
    }




}
