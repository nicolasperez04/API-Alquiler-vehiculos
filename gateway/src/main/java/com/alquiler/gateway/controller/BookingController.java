package com.alquiler.gateway.controller;

import com.alquiler.gateway.client.BookingServiceClient;
import com.alquiler.gateway.dto.BookingDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private BookingServiceClient bookingServiceClient;


    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO booking){
        return bookingServiceClient.createBooking(booking);
    }

    @GetMapping("/pending")
    public ResponseEntity<?> pendingBookings() {
        return bookingServiceClient.pendingBookings();
    }

    @PostMapping("/confirm/{bookingId}")
    public ResponseEntity<?> confirmBooking(@PathVariable Long bookingId) {
        return bookingServiceClient.confirmBooking(bookingId);
    }

    @GetMapping("/getBookingsByUser")
    public ResponseEntity<?> getBookingsByUser() {
        return bookingServiceClient.getBookingsByUser();
    }

    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        String response = bookingServiceClient.cancelBooking(bookingId);
        return ResponseEntity.ok(response);
    }




}
