package com.alquiler.booking_service.repository;

import com.alquiler.booking_service.entity.Booking;
import com.alquiler.booking_service.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


    Object findByCreatedByUsernameAndStatus(String username, BookingStatus bookingStatus);

    List<Booking> findByCreatedByUsername(String username);
}
