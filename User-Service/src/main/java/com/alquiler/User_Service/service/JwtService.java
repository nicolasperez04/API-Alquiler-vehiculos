package com.alquiler.User_Service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Decode the base64 encoded secret key
        return Keys.hmacShaKeyFor(keyBytes); // Create a signing key using the decoded bytes
    }



    private Claims getAllClaims(String token){
        try {
            return Jwts
                    .parser()
                    .setSigningKey(getSignInKey()) // Use the signing key to validate the token
                    .build()
                    .parseClaimsJws(token) // Parse the JWT token and return the claims
                    .getBody();
        }catch (JwtException e){
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public String extractUsername(String token) {
        return getAllClaims(token).getSubject();
    }


    private boolean isTokenExpired(String token) {
        return getAllClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token) {
        try {
            return getAllClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
