package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.User;

public interface IUserService {
    User findUserByUsername(String username);
    User addUser(User user);
    User getUserById(Long id);
    void deleteUserById(Long id);
    User updateUser(Long userId, User user);
    Iterable<User> getAllUsers();
    Iterable<User> getUsersByRole(String role);
    Iterable<User> getUsersByName(String name);
    Iterable<User> getUsersByEmail(String email);
}
