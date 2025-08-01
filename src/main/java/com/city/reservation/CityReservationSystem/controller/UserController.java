package com.city.reservation.CityReservationSystem.controller;

import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public User addUser(User user){
        try{
            if (user == null || user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
                throw new IllegalArgumentException("User details are incomplete");
            }

            if (userService.findUserByUsername(user.getUsername()) != null) {
                throw new IllegalArgumentException("Username already exists");
            }

            return userService.addUser(user);
        }catch (Exception e){
            throw new RuntimeException("Error adding user: " + e.getMessage(), e);
        }
    }
}
