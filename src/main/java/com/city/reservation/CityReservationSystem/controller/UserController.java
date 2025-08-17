package com.city.reservation.CityReservationSystem.controller;

import com.city.reservation.CityReservationSystem.exceptions.EntityNotFoundException;
import com.city.reservation.CityReservationSystem.exceptions.IllegalArgumentException;
import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (user == null || user.getUsername() == null ||
                user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("User details are incomplete");
        }

        if (userService.findUserByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        User createdUser = userService.addUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable Long userId) {
        try{
            if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    }