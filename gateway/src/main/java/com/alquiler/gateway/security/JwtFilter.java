package com.alquiler.gateway.security;

import com.alquiler.gateway.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private static final String[] PUBLIC_URLS = {
            "/auth/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/api-docs/**",
            "/aggregate/**"
    };



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isPublicPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }


        final String authHeader = request.getHeader("Authorization");



        if (authHeader == null || !authHeader.startsWith("Bearer ")){

            filterChain.doFilter(request, response);
            return;
        }



        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);



        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails,jwt,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);


            }

        }



        filterChain.doFilter(request,response);

    }

    private boolean isPublicPath(HttpServletRequest request) {
        String path = request.getRequestURI();
        for (String publicPath : PUBLIC_URLS) {
            if (path.startsWith(publicPath)) {
                return true;
            }
        }
        return false;
    }

}
