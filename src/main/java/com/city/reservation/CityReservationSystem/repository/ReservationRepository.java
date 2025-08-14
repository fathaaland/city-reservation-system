package com.city.reservation.CityReservationSystem.repository;

import com.city.reservation.CityReservationSystem.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findById(long id);
    boolean existsById(long id);

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findBySportFacilityId(Long facilityId);

    List<Reservation> findByStartTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
