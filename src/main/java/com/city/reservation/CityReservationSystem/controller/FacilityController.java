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
    public ResponseEntity<?> addFacility(@RequestBody SportFacility facility) {
        try {
            if (facility == null || facility.getName() == null || facility.getType() == null) {
                return new ResponseEntity<>("Missing required fields", HttpStatus.BAD_REQUEST);
            }
            SportFacility createdFacility = facilityService.addFacility(facility);
            return new ResponseEntity<>(createdFacility, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add facility: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFacilityById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return new ResponseEntity<>("Invalid ID", HttpStatus.BAD_REQUEST);
            }
            SportFacility facility = facilityService.getFacilityById(id);
            return new ResponseEntity<>(facility, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Facility not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFacilityById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return new ResponseEntity<>("Invalid ID", HttpStatus.BAD_REQUEST);
            }
            facilityService.deleteFacilityById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting facility: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update/{facilityId}")
    public ResponseEntity<?> updateFacility(@PathVariable Long facilityId, @RequestBody SportFacility facility) {
        try {
            if (facilityId == null || facilityId <= 0 || facility == null) {
                return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
            }
            SportFacility updatedFacility = facilityService.updateFacility(facilityId, facility);
            return new ResponseEntity<>(updatedFacility, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update facility: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFacilities() {
        try {
            Iterable<SportFacility> facilities = facilityService.getAllFacilities();
            return new ResponseEntity<>(facilities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving facilities", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getFacilitiesByType(@PathVariable SportType type) {
        try {
            if (type == null) {
                return new ResponseEntity<>("Type cannot be null", HttpStatus.BAD_REQUEST);
            }
            Iterable<SportFacility> facilities = facilityService.getFacilitiesByType(type);
            return new ResponseEntity<>(facilities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving facilities by type: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getFacilitiesByName(@PathVariable String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return new ResponseEntity<>("Name cannot be empty", HttpStatus.BAD_REQUEST);
            }
            Iterable<SportFacility> facilities = facilityService.getFacilitiesByName(name);
            return new ResponseEntity<>(facilities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving facilities by name: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
