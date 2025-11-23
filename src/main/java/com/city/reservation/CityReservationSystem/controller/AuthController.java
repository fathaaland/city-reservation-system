package com.city.reservation.CityReservationSystem.controller;

import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.security.JwtUtil;
import com.city.reservation.CityReservationSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (user.getUsername() == null || user.getUsername().isBlank() ||
                    user.getPassword() == null || user.getPassword().isBlank() ||
                    user.getEmail() == null || user.getEmail().isBlank()) {
                return ResponseEntity.badRequest().body("Username, password and email are required");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            if (user.getRole() == null) {
                user.setRole(com.city.reservation.CityReservationSystem.model.enums.Role.USER);
            }

            User createdUser = userService.addUser(user);

            return ResponseEntity.ok(Map.of(
                    "message", "User registered successfully",
                    "userId", createdUser.getId(),
                    "username", createdUser.getUsername()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            if (username == null || password == null) {
                return ResponseEntity.badRequest().body("Username and password are required");
            }

            User user = userService.findUserByUsername(username);

            if (passwordEncoder.matches(password, user.getPassword())) {
                String accessToken = jwtUtil.generateAccessToken(user);
                String refreshToken = jwtUtil.generateRefreshToken(user);

                return ResponseEntity.ok(Map.of(
                        "accessToken", accessToken,
                        "refreshToken", refreshToken,
                        "tokenType", "Bearer",
                        "expiresIn", 15 * 60, // 15 minut v sekundách
                        "username", user.getUsername(),
                        "role", user.getRole().name()
                ));
            } else {
                return ResponseEntity.badRequest().body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");

            if (refreshToken == null) {
                return ResponseEntity.badRequest().body("Refresh token is required");
            }

            if (!jwtUtil.isRefreshToken(refreshToken) || !jwtUtil.isTokenValid(refreshToken)) {
                return ResponseEntity.badRequest().body("Invalid refresh token");
            }

            String newAccessToken = jwtUtil.refreshAccessToken(refreshToken);
            String username = jwtUtil.extractUsername(refreshToken);

            return ResponseEntity.ok(Map.of(
                    "accessToken", newAccessToken,
                    "tokenType", "Bearer",
                    "expiresIn", 15 * 60,
                    "username", username
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Token refresh failed: " + e.getMessage());
        }
    }
}