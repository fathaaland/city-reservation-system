package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IUserService {
    User findUserByUsername(String username);
    User addUser(User user);
    User getUserById(Long id);
    void deleteUserById(Long id);
    User patchUser(Long userId, Map<String, Object> updates);
    Iterable<User> getAllUsers();
    Iterable<User> getUsersByRole(String role, Pageable pageable);
    Iterable<User> getUsersByEmail(String email);
}
