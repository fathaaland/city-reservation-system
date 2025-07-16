package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
 private final UserRepository userRepository;

    @Override
    public User findUserByUsername(String username) {
        return null;
    }

    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public User updateUser(Long userId, User user) {
        return null;
    }

    @Override
    public Iterable<User> getAllUsers() {
        return null;
    }

    @Override
    public Iterable<User> getUsersByRole(String role) {
        return null;
    }

    @Override
    public Iterable<User> getUsersByName(String name) {
        return null;
    }

    @Override
    public Iterable<User> getUsersByEmail(String email) {
        return null;
    }
}
