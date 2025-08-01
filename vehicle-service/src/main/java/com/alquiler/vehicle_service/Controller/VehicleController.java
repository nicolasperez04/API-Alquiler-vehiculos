package com.alquiler.vehicle_service.Controller;

import com.alquiler.vehicle_service.entity.Status;
import com.alquiler.vehicle_service.entity.Type;
import com.alquiler.vehicle_service.entity.Vehicle;
import com.alquiler.vehicle_service.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@AllArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(summary = "Create a new vehicle",
            description = "This endpoint allows an authenticated user with role Seller to create a new vehicle.The statuses that can be used are: AVAILABLE, UNAVAILABLE, IN_MAINTENANCE, RESERVED, RENTED,and the types are: CAR, MOTORCYCLE, TRUCK, BUS, VAN, BICYCLE, SCOOTER")

    @ApiResponse(responseCode = "200",
            description = "Vehicle created successfully",
            content = @Content(schema = @Schema(implementation = Vehicle.class)))

    @ApiResponse(responseCode = "403",
            description = "Forbidden: User does not have permission to create a vehicle",
            content = @Content)

    @PostMapping
    public ResponseEntity<?> createVehicle(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Vehicle details",
            required = true,
            content = @Content(schema = @Schema(implementation = Vehicle.class),
            examples = @ExampleObject(
                    name = "Vehicle Example",
                    value = "{ \"brand\": \"Toyota\", \"model\": \"Corolla\", \"year\": 2020, \"pricePerDay\": 20000, \"type\": \"CAR\", \"status\": \"AVAILABLE\" }"
            ))
    ) @Valid @RequestBody Vehicle vehicle) {
        return vehicleService.createVehicle(vehicle);
    }

    @Operation(summary = "Get vehicle by ID",
            description = "This endpoint retrieves a vehicle by its ID.")

    @ApiResponse(responseCode = "200",
            description = "Vehicle retrieved successfully",
            content = @Content(schema = @Schema(implementation = Vehicle.class)))

    @ApiResponse(responseCode = "404",
            description = "Vehicle not found",
            content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id);
    }


    @Operation(summary = "Update vehicle price",
            description = "This endpoint allows an authenticated user to update the price of a vehicle by its ID.")

    @ApiResponse(responseCode = "200",
            description = "Vehicle price updated successfully",
            content = @Content(schema = @Schema(implementation = Vehicle.class)))

    @ApiResponse(responseCode = "403",
            description = "Forbidden: User does not have permission to update the vehicle price",
            content = @Content)
    @PutMapping("/changePrice/{id}")
    public ResponseEntity<?> changePrice(@PathVariable Long id,@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Vehicle price update request",
            required = true,
            content = @Content(schema = @Schema(implementation = Vehicle.class),
            examples = @ExampleObject(
                    name = "Price Update Example",
                    value = "{ \"pricePerDay\": 25000 }"
            ))
    ) @RequestBody Vehicle request) {
        return vehicleService.updateprice(id, request);
    }

    @Operation(summary = "Filter vehicles by type",
            description = "This endpoint retrieves a list of vehicles filtered by their type.")

    @ApiResponse(responseCode = "200",
            description = "Vehicles filtered by type retrieved successfully",
            content = @Content(schema = @Schema(implementation = Vehicle.class)))

    @ApiResponse(responseCode = "400",
            description = "Invalid type parameter",
            content = @Content)

    @ApiResponse(responseCode = "404",
            description = "No vehicles found for the specified type",
            content = @Content)

    @GetMapping("/filter-type")
    public ResponseEntity<List<Vehicle>> getByType(@RequestParam Type type) {
        return ResponseEntity.ok(vehicleService.filterByType(type));
    }

    @Operation(summary = "Filter vehicles by status",
            description = "This endpoint retrieves a list of vehicles filtered by their status.")

    @ApiResponse(responseCode = "200",
            description = "Vehicles filtered by status retrieved successfully",
            content = @Content(schema = @Schema(implementation = Vehicle.class)))

    @ApiResponse(responseCode = "400",
            description = "Invalid status parameter",
            content = @Content)
    @GetMapping("/filter-status")
    public ResponseEntity<List<Vehicle>> getByStatus(@RequestParam Status status) {
        return ResponseEntity.ok(vehicleService.filterByStatus(status));
    }

    @Operation(summary = "Change vehicle status",
            description = "This endpoint allows an authenticated user to change the status of a vehicle by its ID. The states that can be used are:\n" +
                    " AVAILABLE,\n" +
                    "    UNAVAILABLE,\n" +
                    "    IN_MAINTENANCE,\n" +
                    "    RESERVED,\n" +
                    "    RENTED")

    @ApiResponse(responseCode = "200",
            description = "Vehicle status updated successfully",
            content = @Content(schema = @Schema(implementation = Vehicle.class)))

    @ApiResponse(responseCode = "400",
            description = "Cannot change status from RESERVED to another status",
            content = @Content)

    @ApiResponse(responseCode = "403",
            description = "Forbidden: User does not have permission to change the vehicle status",
            content = @Content)

    @ApiResponse(responseCode = "404",
            description = "Vehicle not found",
            content = @Content)


    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Vehicle status update request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Vehicle.class),
                            examples = @ExampleObject(
                                    name = "Status Update Example",
                                    value = "{ \"status\": \"AVAILABLE\" }"
                            )
                    )
            )
            @RequestBody Vehicle request
    ) {
        return vehicleService.updateStatus(id, request);
    }

    @Operation(summary = "Get all vehicles",
            description = "This endpoint retrieves a list of all vehicles.")

    @ApiResponse(responseCode = "200",
            description = "All vehicles retrieved successfully",
            content = @Content(schema = @Schema(implementation = Vehicle.class)))

    @ApiResponse(responseCode = "404",
            description = "No vehicles found",
            content = @Content)
   @GetMapping("/getVehicles")
    public ResponseEntity<?> getVehicles() {
        return vehicleService.getVehicles();
    }

    @Operation(summary = "Get vehicles reserved by user",
            description = "This endpoint retrieves a list of vehicles reserved by the authenticated user.")

    @ApiResponse(responseCode = "200",
            description = "Vehicles reserved by user retrieved successfully",
            content = @Content(schema = @Schema(implementation = Vehicle.class)))

    @ApiResponse(responseCode = "404",
            description = "No vehicles reserved by the user",
            content = @Content)

    @GetMapping("/getVehiclesReserved")
    public ResponseEntity<?> getVehiclesReservedByUser(){
        return vehicleService.getVehiclesReservedByUser();
    }



}
