package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        return User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .reservations(user.getReservations())
                .build();
    }

    @Override
    public User addUser(User user) {
        try {
            User newUser = createUser(user);
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new RuntimeException("Error adding user: " + e.getMessage(), e);
        }
    }

    @Override
    public User getUserById(Long id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Error getting user: " + e.getMessage(), e);
        }
    }

    @Override
    public User findUserByUsername(String userName) {
        try {
            return userRepository.findByUsername(userName);
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by username: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long userId, User user) {
        return null;
    }

    @Override
    public Iterable<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all users: " + e.getMessage(), e);
        }
    }

    @Override
    public Iterable<User> getUsersByRole(String role, Pageable pageable) {
        try {
            return userRepository.findByRoleIn(Collections.singletonList(role), pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error getting users by role: " + e.getMessage(), e);
        }
    }

    @Override
    public Iterable<User> getUsersByEmail(String email) {
        try {
            User user = userRepository.findByEmail(email);

            if (user == null) {
                return Collections.emptyList();
            }
            return Collections.singleton(user);
        } catch (Exception e) {
            throw new RuntimeException("Error getting users by email: " + e.getMessage(), e);
        }
    }

}
