package com.alquiler.booking_service.controller;


import com.alquiler.booking_service.entity.Booking;
import com.alquiler.booking_service.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingController {

    private BookingService bookingService;

    @Operation(summary = "Create a new booking",
            description = "This endpoint allows a user to create a new booking for a vehicle.")

    @ApiResponse(responseCode = "200",
            description = "Booking created successfully",
            content = @io.swagger.v3.oas.annotations.media.Content)

    @ApiResponse(responseCode = "400",
            description = "Invalid booking data or vehicle not available",
            content = @io.swagger.v3.oas.annotations.media.Content)

    @ApiResponse(responseCode = "403",
            description = "Access denied for non-authenticated users",
            content = @io.swagger.v3.oas.annotations.media.Content)

    @ApiResponse(responseCode = "500",
            description = "Internal server error",
            content = @io.swagger.v3.oas.annotations.media.Content)

    @PostMapping
    public ResponseEntity<?> createBooking(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Booking details",
            required = true,
            content = @Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Booking.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Booking Example",
                    value = "{ \"vehicleId\": 1, \"startDate\": \"2025-07-01\", \"endDate\": \"2025-07-10\" }"
            ))
    ) @RequestBody Booking booking) {
        return bookingService.createBooking(booking);

    }

    @Operation(summary = "Get booking by ID",
            description = "This endpoint shows pending reservations that have yet to be confirmed.")

    @ApiResponse(responseCode = "200",
            description = "Booking retrieved successfully",
            content = @Content)

    @ApiResponse(responseCode = "404",
            description = "Booking not found",
            content = @Content)

    @GetMapping("/pending")
    public ResponseEntity<?> pendingBookings() {
        return bookingService.pendingBookings();
    }

    @Operation(summary = "Confirm a booking",
            description = "This endpoint allows the vehicle owner to accept the reservation that the other user wants to make.")

    @ApiResponse(responseCode = "200",
            description = "Booking confirmed successfully",
            content = @Content)

    @ApiResponse(responseCode = "404",
            description = "Booking not found",
            content = @Content)
    @PostMapping("/confirm/{bookingId}")
    public ResponseEntity<?> confirmBooking(@PathVariable Long bookingId) {
        return bookingService.confirmBooking(bookingId);
    }

    @Operation(summary = "Get bookings by user",
            description = "This endpoint retrieves all bookings made by the authenticated user.")

    @ApiResponse(responseCode = "200",
            description = "Bookings retrieved successfully",
            content = @Content)

    @ApiResponse(responseCode = "404",
            description = "No bookings found for this user",
            content = @Content)
    @ApiResponse(responseCode = "403",
            description = "Access denied for non-authenticated users",
            content = @Content)
    @GetMapping("/getBookingsByUser")
    public ResponseEntity<?> getBookingsByUser() {
        return bookingService.getBookingsByUser();
    }
    @Operation(summary = "Cancel a booking",
            description = "This endpoint allows a user to cancel a booking by its ID.")

    @ApiResponse(responseCode = "200",
            description = "Booking cancelled successfully",
            content = @Content)

    @ApiResponse(responseCode = "404",
            description = "Booking not found",
            content = @Content)
    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        return bookingService.cancelBooking(bookingId);
    }
}
