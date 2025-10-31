package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.Reservation;

import java.util.List;

public interface IReservationService {
    Reservation addReservation(Reservation reservation);
    Reservation getReservationById(Long id);
    void deleteReservationById(Long id);
    Reservation updateReservation(Long reservationId, Reservation reservation);
    List<Reservation> getAllReservations();
    List<Reservation> getReservationsByUserId(Long userId);
    List<Reservation> getReservationsByFacilityId(Long facilityId);
    List<Reservation> getReservationsByDate(String date);
}