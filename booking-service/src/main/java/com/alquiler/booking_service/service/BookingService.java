package com.alquiler.booking_service.service;

import com.alquiler.booking_service.Client.VehicleFeignClient;
import com.alquiler.booking_service.dto.vehicle.Status;
import com.alquiler.booking_service.dto.vehicle.VehicleDTO;
import com.alquiler.booking_service.entity.Booking;
import com.alquiler.booking_service.entity.BookingStatus;
import com.alquiler.booking_service.kafka.KafkaTopic;
import com.alquiler.booking_service.repository.BookingRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {

    private BookingRepository bookingRepository;

    private VehicleFeignClient vehicleFeignClient;

    private KafkaTopic kafkaTopic;

    @CircuitBreaker(name = "vehicleService", fallbackMethod = "vehicleFallback")
    public ResponseEntity<?> createBooking(Booking booking){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        VehicleDTO vehicle = vehicleFeignClient.getVehicleById(booking.getVehicleId()).getBody();

        if (vehicle == null || vehicle.getPricePerDay() == null) {
            return ResponseEntity.badRequest().body("Invalid vehicle or price not set");
        }

        Status statusVehicle = Status.valueOf(vehicle.getStatus());

        if (statusVehicle == Status.RESERVED) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Vehicle is already reserved");
        }

        long days = ChronoUnit.DAYS.between(
                booking.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                booking.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );

        double totalPrice = days * vehicle.getPricePerDay();

        Booking newBooking = Booking.builder()
                .createdByUsername(username)
                .vehicleId(booking.getVehicleId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .status(BookingStatus.PENDING)
                .totalPrice(totalPrice)
                .build();


        return ResponseEntity.ok(bookingRepository.save(newBooking));


    }

    public ResponseEntity<?> vehicleFallback(Booking booking, Throwable ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Vehicle service is currently unavailable. Please try again later.");
    }


    public ResponseEntity<?> pendingBookings() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(bookingRepository.findByCreatedByUsernameAndStatus(username, BookingStatus.PENDING));
    }

    public ResponseEntity<?> confirmBooking(Long bookingId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }
        Booking booking = optionalBooking.get();

        VehicleDTO vehicle = vehicleFeignClient.getVehicleById(booking.getVehicleId()).getBody();

        if (!vehicle.getCreatedByUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to confirm this booking");
        }

        booking.setStatus(BookingStatus.CONFIRMED);

        bookingRepository.save(booking);
        kafkaTopic.ChangeStatusBookedVehicle(booking);

        return ResponseEntity.ok(booking);
    }

    public ResponseEntity<?> getBookingsByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Booking> bookings = bookingRepository.findByCreatedByUsername(username);

        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bookings found for this user");
        }

        return ResponseEntity.ok(bookings);
    }

    public ResponseEntity<?> cancelBooking(Long bookingId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }
        Booking booking = optionalBooking.get();

        if (!booking.getCreatedByUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to cancel this booking");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        kafkaTopic.ChangeStatusCancelledVehicle(booking);

        String message = "Booking with ID " + bookingId + " has been cancelled by user " + username;

        return ResponseEntity.ok(message);
    }


}
