package com.city.reservation.CityReservationSystem.controller;

import com.city.reservation.CityReservationSystem.model.entity.Reservation;
import com.city.reservation.CityReservationSystem.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addReservation(@RequestBody Reservation reservation) {
        if (reservation == null || reservation.getUser() == null || reservation.getSportFacility() == null ||
                reservation.getStartTime() == null || reservation.getEndTime() == null) {
            return new ResponseEntity<>("Missing required fields", HttpStatus.BAD_REQUEST);
        }
        Reservation createdReservation = reservationService.addReservation(reservation);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Reservation reservation = reservationService.getReservationById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReservationById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>("Invalid reservation ID", HttpStatus.BAD_REQUEST);
        }
        reservationService.deleteReservationById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<List<Reservation>> getReservationsByFacilityId(@PathVariable Long facilityId) {
        if (facilityId == null || facilityId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Reservation> reservations = reservationService.getReservationsByFacilityId(facilityId);
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Map<String, Object> reservationDetails) {
        if (id == null || id <= 0 || reservationDetails == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Reservation updatedReservation = reservationService.updateReservation(id, reservationDetails);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/date/{reservationDate}")
    public ResponseEntity<List<Reservation>> getReservationsByDate(@PathVariable String reservationDate) {
        List<Reservation> reservations = reservationService.getReservationsByDate(reservationDate);
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}