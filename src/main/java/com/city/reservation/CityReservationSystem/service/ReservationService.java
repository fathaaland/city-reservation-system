package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.Reservation;
import com.city.reservation.CityReservationSystem.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService implements IReservationService  {
    private final ReservationRepository reservationRepository;


    @Override
    public Reservation addReservation(Reservation reservation) {
        return null;
    }

    @Override
    public Reservation getReservationById(Long id) {
        return null;
    }

    @Override
    public void deleteReservationById(Long id) {

    }

    @Override
    public Reservation updateReservation(Long reservationId, Reservation reservation) {
        return null;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return List.of();
    }

    @Override
    public List<Reservation> getReservationsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<Reservation> getReservationsByFacilityId(Long facilityId) {
        return List.of();
    }

    @Override
    public List<Reservation> getReservationsByDate(String date) {
        return List.of();
    }

    @Override
    public List<Reservation> getReservationsByUserIdAndFacilityId(Long userId, Long facilityId) {
        return List.of();
    }

    @Override
    public Long countReservationsByUserId(Long userId, Long facilityId) {
        return 0L;
    }

    @Override
    public List<Reservation> getReservationsByUserIdAndDate(Long userId, String date) {
        return List.of();
    }

    @Override
    public List<Reservation> getReservationsByFacilityIdAndDate(Long facilityId, String date) {
        return List.of();
    }
}
