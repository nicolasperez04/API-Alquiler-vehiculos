package com.alquiler.vehicle_service.client;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(FeignException.Forbidden.class)
    public ResponseEntity<String> handleForbidden(FeignException.Forbidden ex) {

        String message = ex.contentUTF8();
        return ResponseEntity.status(403).body(message);
    }

}
