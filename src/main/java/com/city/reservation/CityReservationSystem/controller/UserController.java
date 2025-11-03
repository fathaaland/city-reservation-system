package com.city.reservation.CityReservationSystem.controller;

import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            throw new IllegalArgumentException("User details are incomplete");
        }

        User createdUser = userService.addUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        userService.deleteUserById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/by-username/{userName}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String userName) {
        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        User user = userService.findUserByUsername(userName);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role/{userRole}")
    public ResponseEntity<Iterable<User>> getUsersByRole(@PathVariable String userRole) {
        if (userRole == null || userRole.isBlank()) {
            throw new IllegalArgumentException("User role cannot be empty");
        }

        Iterable<User> roleUsers = userService.getUsersByRole(userRole, null);
        return ResponseEntity.ok(roleUsers);
    }

    @GetMapping("/mail/{userMail}")
    public ResponseEntity<Iterable<User>> getUsersByEmail(@PathVariable String userMail) {
        if (userMail == null || userMail.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        Iterable<User> mailUser = userService.getUsersByEmail(userMail);
        return ResponseEntity.ok(mailUser);
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<User> patchUser(@PathVariable Long userId, @RequestBody Map<String, Object> updates) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        if (updates == null || updates.isEmpty()) {
            throw new IllegalArgumentException("No updates provided");
        }

        User updatedUser = userService.patchUser(userId, updates);
        return ResponseEntity.ok(updatedUser);
    }
}
