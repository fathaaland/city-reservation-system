package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.exceptions.InternalServerException;
import com.city.reservation.CityReservationSystem.exceptions.ResourceNotFoundException;
import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    private User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        return User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password((user.getPassword()))
                .role(user.getRole())
                .reservations(user.getReservations())
                .build();
    }

    @Override
    public User addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        User newUser = createUser(user);
        return userRepository.save(newUser);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }

    @Override
    public User findUserByUsername(String userName) {
        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new EntityNotFoundException("User not found with username: " + userName);
        }

        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public User patchUser(Long userId, Map<String, Object> updates) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        if (updates == null || updates.isEmpty()) {
            throw new IllegalArgumentException("No updates provided");
        }

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        updates.forEach((key, value) -> {
            switch (key) {
                case "username" -> existingUser.setUsername((String) value);
                case "email" -> existingUser.setEmail((String) value);
                case "password" -> existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
                default -> throw new IllegalArgumentException("Unknown field: " + key);
            }
        });

        return userRepository.save(existingUser);
    }

    @Override
    public Iterable<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        if (!users.iterator().hasNext()) {
            throw new EntityNotFoundException("No users found");
        }
        return users;
    }

    @Override
    public Iterable<User> getUsersByRole(String role, Pageable pageable) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role cannot be empty");
        }

        Iterable<User> users = userRepository.findByRoleIn(Collections.singletonList(role), pageable);
        if (!users.iterator().hasNext()) {
            throw new EntityNotFoundException("No users found with role: " + role);
        }

        return users;
    }

    @Override
    public Iterable<User> getUsersByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }

        return Collections.singleton(user);
    }
}
