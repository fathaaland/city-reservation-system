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
}
