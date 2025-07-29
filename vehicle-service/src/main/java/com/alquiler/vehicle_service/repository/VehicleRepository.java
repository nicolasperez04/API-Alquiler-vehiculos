package com.alquiler.vehicle_service.repository;

import com.alquiler.vehicle_service.entity.Status;
import com.alquiler.vehicle_service.entity.Type;
import com.alquiler.vehicle_service.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByStatus(Status status);

    List<Vehicle> findByType(Type type);

    List<Vehicle> findByCreatedByUsername(String username);


}
