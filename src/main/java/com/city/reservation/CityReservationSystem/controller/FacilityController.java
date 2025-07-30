package com.city.reservation.CityReservationSystem.controller;

import com.city.reservation.CityReservationSystem.model.entity.SportFacility;
import com.city.reservation.CityReservationSystem.model.enums.SportType;
import com.city.reservation.CityReservationSystem.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facilities")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

    @PostMapping("/add")
    public ResponseEntity<SportFacility> addFacility(@RequestBody SportFacility facility) {
        SportFacility createdFacility = facilityService.addFacility(facility);
        return new ResponseEntity<>(createdFacility, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportFacility> getFacilityById(@PathVariable Long id) {
        SportFacility facility = facilityService.getFacilityById(id);
        if (facility != null) {
            return new ResponseEntity<>(facility, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFacilityById(@PathVariable Long id) {
        try {
            facilityService.deleteFacilityById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PutMapping("/update/{facilityId}")
    public ResponseEntity<SportFacility> updateFacility(@PathVariable Long facilityId, @RequestBody SportFacility facility) {
        try {
            SportFacility updatedFacility = facilityService.updateFacility(facilityId, facility);
            return new ResponseEntity<>(updatedFacility, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<SportFacility>> getAllFacilities() {
        Iterable<SportFacility> facilities = facilityService.getAllFacilities();
        if (facilities != null) {
            return new ResponseEntity<>(facilities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<Iterable<SportFacility>> getFacilitiesByType(@PathVariable SportType type) {
        Iterable<SportFacility> facilities = facilityService.getFacilitiesByType(type);
        if (facilities != null) {
            return new ResponseEntity<>(facilities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }
}