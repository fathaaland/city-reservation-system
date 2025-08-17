package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
     try{
         User newUser = createUser(user);
         return userRepository.save(newUser);
     }catch(Exception e) {
         throw new RuntimeException("Error adding user: " + e.getMessage(), e);
     }
    }

    @Override
    public User getUserById(Long id) {
        try{
            return userRepository.findById(id).orElse(null);

    } catch (Exception e) {
            throw new RuntimeException("Error getting user: " + e.getMessage(), e);
        }
    }


    @Override
    public User findUserByUsername(String username) {
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
