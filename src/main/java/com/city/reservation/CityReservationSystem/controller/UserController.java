package com.city.reservation.CityReservationSystem.controller;

import com.city.reservation.CityReservationSystem.exceptions.BadRequestException;
import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (user == null ||
                user.getUsername() == null || user.getUsername().isBlank() ||
                user.getEmail() == null || user.getEmail().isBlank() ||
                user.getPassword() == null || user.getPassword().isBlank()) {
            throw new BadRequestException("User details are incomplete",HttpStatus.BAD_REQUEST );

        }

        User createdUser = userService.addUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            throw new BadRequestException("Invalid user ID", HttpStatus.BAD_REQUEST);
        }



        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            throw new BadRequestException("Invalid user ID", HttpStatus.BAD_REQUEST);
        }

        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/by-username/{userName}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String userName) {
        if (userName == null || userName.isBlank()) {
            throw new BadRequestException("Username cannot be empty", HttpStatus.BAD_REQUEST);
        }

        User user = userService.findUserByUsername(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/role/{userRole}")
    public ResponseEntity<Iterable<User>> getUsersByRole(@PathVariable String userRole) {
        if (userRole == null || userRole.isBlank()) {
            throw new BadRequestException("User role cannot be empty", HttpStatus.BAD_REQUEST);
        }

        Iterable<User> roleUsers = userService.getUsersByRole(userRole, null);
        return new ResponseEntity<>(roleUsers, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/mail/{userMail}")
    public ResponseEntity<Iterable<User>> getUsersByEmail(@PathVariable String userMail) {
        if (userMail == null || userMail.isBlank()) {
            throw new BadRequestException("Email role cannot be empty", HttpStatus.BAD_REQUEST);

        }
        Iterable<User> mailUser = userService.getUsersByEmail(userMail);
        return new ResponseEntity<>(mailUser, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{userId}")
    public ResponseEntity<User> patchUser(@PathVariable Long userId, @RequestBody Map<String, Object> updates) {
        if (userId == null || userId <= 0) {
            throw new BadRequestException("Invalid user ID", HttpStatus.BAD_REQUEST);
        }

        if (updates == null || updates.isEmpty()) {
            throw new BadRequestException("No updates provided", HttpStatus.BAD_REQUEST);

        }

        User updatedUser = userService.patchUser(userId, updates);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}