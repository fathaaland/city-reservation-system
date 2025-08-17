package com.city.reservation.CityReservationSystem.controller;

import com.city.reservation.CityReservationSystem.model.entity.SportFacility;
import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.repository.UserRepository;
import com.city.reservation.CityReservationSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/add")
    public User addUser(@RequestBody User user){
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

    @GetMapping("/{userId}")
    public User findUserById(@PathVariable Long userId){
        try{
            if(!userRepository.existsById(userId)) {
                throw new RuntimeException("User with id " + userId + " not found");
            }

            if(userId <= 0) {
                throw new IllegalArgumentException("User details are incomplete or invalid");
            }

            return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
