package com.alquiler.gateway.client;

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

    @org.springframework.web.bind.annotation.ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<String> handleNotFound(FeignException.NotFound ex) {

        String message = ex.contentUTF8();
        return ResponseEntity.status(404).body(message);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<String> handleBadRequest(FeignException.BadRequest ex) {

        String message = ex.contentUTF8();
        return ResponseEntity.status(400).body(message);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(FeignException.Conflict.class)
    public ResponseEntity<String> handleConflict(FeignException.Conflict ex) {

        String message = ex.contentUTF8();
        return ResponseEntity.status(409).body(message);
    }

}
