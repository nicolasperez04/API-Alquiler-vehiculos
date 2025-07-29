package com.alquiler.booking_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;


    private Key getSignInKey(){
     byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
     return Keys.hmacShaKeyFor(keyBytes);
    }


    private Claims getAllClaims(String token){
        try {
          return Jwts
                  .parser()
                  .setSigningKey(getSignInKey())
                  .build()
                  .parseClaimsJws(token)
                  .getBody();
        } catch (JwtException e){
            throw new RuntimeException("Invalid JWT token", e);
        }
    }


    public String extractUsername(String token){
        return getAllClaims(token).getSubject();
    }


    private Boolean isTokenExpired(String token){
    return getAllClaims(token).getExpiration().before(new Date());
    }


    public Boolean isTokenValid(String token){

        try {
          return getAllClaims(token).getExpiration().after(new Date());
        }catch (Exception e){
            return false;
        }
    }



}
