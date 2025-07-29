package com.alquiler.booking_service.controller;


import com.alquiler.booking_service.entity.Booking;
import com.alquiler.booking_service.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingController {

    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);

    }

    @GetMapping("/pending")
    public ResponseEntity<?> pendingBookings() {
        return bookingService.pendingBookings();
    }

    @PostMapping("/confirm/{bookingId}")
    public ResponseEntity<?> confirmBooking(@PathVariable Long bookingId) {
        return bookingService.confirmBooking(bookingId);
    }

    @GetMapping("/getBookingsByUser")
    public ResponseEntity<?> getBookingsByUser() {
        return bookingService.getBookingsByUser();
    }

    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        return bookingService.cancelBooking(bookingId);
    }
}
