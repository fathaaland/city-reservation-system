package com.city.reservation.CityReservationSystem.repository;

import com.city.reservation.CityReservationSystem.model.entity.SportFacility;
import com.city.reservation.CityReservationSystem.model.enums.SportType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<SportFacility, Long> {
    SportFacility findByName(String name);

    boolean existsByName(String name);

    Iterable<SportFacility> findByType(SportType type);

    boolean existsByType(SportType type);
}
