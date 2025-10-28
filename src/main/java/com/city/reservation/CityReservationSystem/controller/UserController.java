package com.city.reservation.CityReservationSystem.controller;

import com.city.reservation.CityReservationSystem.exceptions.EntityNotFoundException;
import com.city.reservation.CityReservationSystem.exceptions.IllegalArgumentException;
import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        try {
            if (userId <= 0) {
                throw new IllegalArgumentException("Invalid user ID");
            }

            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        if (userId <= 0) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }

        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/by-username/{userName}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String userName) {
        if (userName == null || userName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userService.findUserByUsername(userName);
        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        try {
            Iterable<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/role/{userRole}")
    public ResponseEntity<Iterable<User>> getUsersByRole(@PathVariable String userRole) {
        try {
            if (userRole == null || userRole.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Iterable<User> roleUsers = userService.getUsersByRole(userRole, null);
            return ResponseEntity.ok(roleUsers);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/mail/{userMail}")
    public ResponseEntity<Iterable<User>> getUsersByEmail(@PathVariable String userMail) {
        try {
            if (userMail == null || userMail.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Iterable<User> mailUser = userService.getUsersByEmail(userMail);

            if (!mailUser.iterator().hasNext()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(mailUser);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<User> patchUser(@PathVariable Long userId, @RequestBody Map<String, Object> updates) {
        try {
            User updatedUser = userService.patchUser(userId, updates);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
