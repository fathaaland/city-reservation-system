package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.Reservation;
import com.city.reservation.CityReservationSystem.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService implements IReservationService  {
    private final ReservationRepository reservationRepository;



    private Reservation createReservation(Reservation reservation){
        return Reservation.builder()
                .user(reservation.getUser())
                .sportFacility(reservation.getSportFacility())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .build();
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        try {
            if (reservationRepository.findById(reservation.getId()).isEmpty()) {
                throw new RuntimeException("User or Facility not found");
            }
            if (reservation.getStartTime().isAfter(reservation.getEndTime())) {
                throw new RuntimeException("Start time must be before end time");
            }
            if (reservation.getStartTime().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Start time cannot be in the past");
            }
            if (reservation.getEndTime().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("End time cannot be in the past");
            }

            Reservation newReservation = createReservation(reservation);
            return reservationRepository.save(newReservation);
        } catch (Exception e) {
            throw new RuntimeException("Error adding reservation: " + e.getMessage(), e);
        }
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
