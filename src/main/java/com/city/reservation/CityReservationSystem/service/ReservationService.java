package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.exceptions.BadRequestException;
import com.city.reservation.CityReservationSystem.exceptions.EntityNotFoundException;
import com.city.reservation.CityReservationSystem.model.entity.Reservation;
import com.city.reservation.CityReservationSystem.model.entity.SportFacility;
import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.repository.ReservationRepository;
import com.city.reservation.CityReservationSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.data.crossstore.ChangeSetPersister;
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
                .orElseThrow(() -> new EntityNotFoundException ("User not found with id: " + userId));
        SportFacility sportFacility = FacilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Sport facility not found with id: " + facilityId));

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
            throw new InternalException("Error adding reservation: " + e.getMessage(), e);
        }
    }

    @Override
    public Reservation getReservationById(Long id) {
      try{
            return reservationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }catch (Exception e){
            throw new InternalException("Error retrieving reservation with id: " + id + " - " + e.getMessage(), e);
      }
    }

    @Override
    public void deleteReservationById(Long id) {
        try {
            if (!reservationRepository.existsById(id)) {
                throw new EntityNotFoundException("Reservation not found with id: " + id);
            }
            reservationRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalException("Error deleting reservation with id: " + id + " - " + e.getMessage(), e);
        }

    }

    @Override
    public List<Reservation> getAllReservations() {
        try {
            if (reservationRepository.count() == 0) {
                throw new EntityNotFoundException("No reservations found.");
            }

            return reservationRepository.findAll();
        } catch (Exception e) {
            throw new InternalException("Error retrieving all reservations: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Reservation> getReservationsByUserId(Long userId) {
        try {
            if (!userRepository.existsById(userId)) {
                throw new EntityNotFoundException("User not found with id: " + userId);
            }
            return reservationRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new InternalException("Error retrieving reservations for user with id: " + userId + " - " + e.getMessage(), e);
        }
    }

    @Override
    public List<Reservation> getReservationsByFacilityId(Long facilityId) {
        try{
            if(!FacilityRepository.existsById(facilityId)) {
                throw new EntityNotFoundException("Facility not found with id: " + facilityId);
            }

            return reservationRepository.findBySportFacilityId(facilityId);
        }catch (Exception e) {
            throw new InternalException("Error retrieving reservations for facility with id: " + facilityId + " - " + e.getMessage(), e);
        }
    }

    @Override
    public List<Reservation> getReservationsByDate(String date) {
        try{
            LocalDateTime startOfDay = LocalDateTime.parse(date + "T00:00:00");
            LocalDateTime endOfDay = LocalDateTime.parse(date + "T23:59:59");

            return reservationRepository.findByStartTimeBetween(startOfDay, endOfDay);
        } catch (Exception e) {
            throw new BadRequestException("Error retrieving reservations by date " + date + " - " + e.getMessage());
        }
    }

    @Override
    public Reservation updateReservation(Long reservationId, Reservation reservation) {
        try {

            Reservation existingReservation = reservationRepository.findById(reservationId)
                    .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));

            if (reservation.getStartTime().isAfter(reservation.getEndTime())) {
                throw new BadRequestException("Start time must be before end time.");
            }
            existingReservation.setStartTime(reservation.getStartTime());
            existingReservation.setEndTime(reservation.getEndTime());

            return reservationRepository.save(existingReservation);
        } catch (Exception e) {
            throw new InternalException("Error updating reservation with id: " + reservationId + " - " + e.getMessage(), e);
        }
    }

}
