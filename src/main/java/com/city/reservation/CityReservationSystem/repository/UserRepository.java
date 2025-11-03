package com.city.reservation.CityReservationSystem.repository;

import com.city.reservation.CityReservationSystem.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    List<User> findByRoleIn(Collection<String> roles, Pageable pageable);

}
