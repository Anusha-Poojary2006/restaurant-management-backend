package com.restaurant.restaurant_backend.config;

import com.restaurant.restaurant_backend.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Login and signup
                        .requestMatchers("/auth/**")
                        .permitAll()

                        // =========================
                        // MENU
                        // =========================

                        // Admin can add menu item
                        .requestMatchers(
                                HttpMethod.POST,
                                "/menu"
                        )
                        .hasRole("ADMIN")

                        // Admin can update menu item
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/menu/**"
                        )
                        .hasRole("ADMIN")

                        // Admin can delete menu item
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/menu/**"
                        )
                        .hasRole("ADMIN")

                        // Customer/Admin can view menu
                        .requestMatchers(
                                HttpMethod.GET,
                                "/menu/**"
                        )
                        .hasAnyRole("ADMIN", "CUSTOMER")

                        // =========================
                        // CUSTOMER ORDERS
                        // =========================

                        // Customer places order
                        .requestMatchers(
                                HttpMethod.POST,
                                "/order"
                        )
                        .hasRole("CUSTOMER")

                        // Customer views own orders
                        .requestMatchers(
                                HttpMethod.GET,
                                "/my-orders"
                        )
                        .hasRole("CUSTOMER")

                        // =========================
                        // ADMIN ORDERS
                        // =========================

                        // Admin views all orders
                        .requestMatchers(
                                HttpMethod.GET,
                                "/orders"
                        )
                        .hasRole("ADMIN")

                        // Admin changes order status
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/order/*/status"
                        )
                        .hasRole("ADMIN")

                        // Everything else requires login
                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}