package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.Reservation;
import com.city.reservation.CityReservationSystem.model.entity.SportFacility;
import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.repository.ReservationRepository;
import com.city.reservation.CityReservationSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.city.reservation.CityReservationSystem.repository.FacilityRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService implements IReservationService  {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final FacilityRepository FacilityRepository;




    private Reservation createReservation(Reservation reservation) {
        Long userId = reservation.getUser().getId();
        Long facilityId = reservation.getSportFacility().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        SportFacility sportFacility = FacilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Sport facility not found with id: " + facilityId));

        return Reservation.builder()
                .user(user)
                .sportFacility(sportFacility)
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .build();
    }


    @Override
    public Reservation addReservation(Reservation reservation) {
        try {
            Reservation newReservation = createReservation(reservation);
            return reservationRepository.save(newReservation);
        } catch (Exception e) {
            throw new RuntimeException("Error adding reservation: " + e.getMessage(), e);
        }
    }

    @Override
    public Reservation getReservationById(Long id) {
      try{
          if (!reservationRepository.existsById(id)) {
              throw new RuntimeException("Reservation not found with id: " + id);
          }

            return reservationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }catch (Exception e){
            throw new RuntimeException("Error retrieving reservation with id: " + id + " - " + e.getMessage(), e);
      }
    }

    @Override
    public void deleteReservationById(Long id) {
        try {
            if (!reservationRepository.existsById(id)) {
                throw new RuntimeException("Reservation not found with id: " + id);
            }
            reservationRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting reservation with id: " + id + " - " + e.getMessage(), e);
        }

    }

    @Override
    public Reservation updateReservation(Long reservationId, Reservation reservation) {
        return null;
    }

    @Override
    public List<Reservation> getAllReservations() {
        try {

            if (reservationRepository.count() == 0) {
                throw new RuntimeException("No reservations found.");
            }

            return reservationRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all reservations: " + e.getMessage(), e);
        }
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
