package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.exceptions.BadRequestException;
import com.city.reservation.CityReservationSystem.exceptions.EntityNotFoundException;
import com.city.reservation.CityReservationSystem.exceptions.InternalServerException;
import com.city.reservation.CityReservationSystem.model.entity.Reservation;
import com.city.reservation.CityReservationSystem.model.entity.SportFacility;
import com.city.reservation.CityReservationSystem.model.entity.User;
import com.city.reservation.CityReservationSystem.model.enums.SportType;
import com.city.reservation.CityReservationSystem.repository.FacilityRepository;
import com.city.reservation.CityReservationSystem.repository.ReservationRepository;
import com.city.reservation.CityReservationSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final FacilityRepository facilityRepository;


    private Reservation createReservation(Reservation reservation) {
        if (reservation == null) {
            throw new BadRequestException("Reservation cannot be null.");
        }

        if (reservation.getUser() == null || reservation.getSportFacility() == null) {
            throw new BadRequestException("Reservation must include user and facility.");
        }

        Long userId = reservation.getUser().getId();
        Long facilityId = reservation.getSportFacility().getId();

        if (userId == null || facilityId == null) {
            throw new BadRequestException("User ID and Facility ID cannot be null.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        SportFacility sportFacility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Sport facility not found with id: " + facilityId));

        if (reservation.getStartTime() == null || reservation.getEndTime() == null) {
            throw new BadRequestException("Start and end times are required.");
        }

        if (reservation.getStartTime().isAfter(reservation.getEndTime())) {
            throw new BadRequestException("Start time must be before end time.");
        }

        return Reservation.builder()
                .user(user)
                .sportFacility(sportFacility)
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .build();
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        Reservation newReservation = createReservation(reservation);
        return reservationRepository.save(newReservation);
    }

    @Override
    public Reservation getReservationById(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Invalid reservation ID.");
        }

        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
    }

    @Override
    public void deleteReservationById(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Invalid reservation ID.");
        }

        if (!reservationRepository.existsById(id)) {
            throw new EntityNotFoundException("Reservation not found with id: " + id);
        }

        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservationsByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new BadRequestException("Invalid user ID.");
        }

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }

        return reservationRepository.findByUserId(userId);
    }

    @Override
    public List<Reservation> getReservationsByFacilityId(Long facilityId) {
        if (facilityId == null || facilityId <= 0) {
            throw new BadRequestException("Invalid facility ID.");
        }

        if (!facilityRepository.existsById(facilityId)) {
            throw new EntityNotFoundException("Facility not found with id: " + facilityId);
        }

        return reservationRepository.findBySportFacilityId(facilityId);
    }

    @Override
    public List<Reservation> getReservationsByDate(String date) {
        if (date == null || date.isEmpty()) {
            throw new BadRequestException("Date cannot be null or empty.");
        }

        try {
            LocalDate parsedDate = LocalDate.parse(date);
            LocalDateTime startOfDay = parsedDate.atStartOfDay();
            LocalDateTime endOfDay = parsedDate.atTime(23, 59, 59);

            return reservationRepository.findByStartTimeBetween(startOfDay, endOfDay);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid date format. Expected format: yyyy-MM-dd");
        }
    }

    @Override
    public Reservation updateReservation(Long reservationId, Map<String, Object> updates) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));

        updates.forEach((key, value) -> {
            switch (key) {
                case "startTime" -> {
                    if (value instanceof String) {
                        existingReservation.setStartTime(LocalDateTime.parse((String) value));
                    } else if (value instanceof LocalDateTime) {
                        existingReservation.setStartTime((LocalDateTime) value);
                    } else {
                        throw new IllegalArgumentException("Invalid format for startTime. Expected String or LocalDateTime.");
                    }
                }
                case "endTime" -> {
                    if (value instanceof String) {
                        existingReservation.setEndTime(LocalDateTime.parse((String) value));
                    } else if (value instanceof LocalDateTime) {
                        existingReservation.setEndTime((LocalDateTime) value);
                    } else {
                        throw new IllegalArgumentException("Invalid format for endTime. Expected String or LocalDateTime.");
                    }
                }
                default -> throw new IllegalArgumentException("Unknown field: " + key);
            }
        });

        if (existingReservation.getStartTime() != null && existingReservation.getEndTime() != null) {
            if (existingReservation.getStartTime().isAfter(existingReservation.getEndTime())) {
                throw new BadRequestException("Start time must be before end time.");
            }
        }

        return reservationRepository.save(existingReservation);
    }

}
