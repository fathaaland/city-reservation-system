package com.city.reservation.CityReservationSystem.controller;

import com.city.reservation.CityReservationSystem.model.entity.SportFacility;
import com.city.reservation.CityReservationSystem.model.enums.SportType;
import com.city.reservation.CityReservationSystem.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/facilities")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addFacility(@RequestBody SportFacility facility) {
        if (facility == null || facility.getName() == null || facility.getType() == null) {
            return new ResponseEntity<>("Missing required fields", HttpStatus.BAD_REQUEST);
        }
        SportFacility createdFacility = facilityService.addFacility(facility);
        return new ResponseEntity<>(createdFacility, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getFacilityById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>("Invalid ID", HttpStatus.BAD_REQUEST);
        }
        SportFacility facility = facilityService.getFacilityById(id);
        return new ResponseEntity<>(facility, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFacilityById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>("Invalid ID", HttpStatus.BAD_REQUEST);
        }
        facilityService.deleteFacilityById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/update/{facilityId}")
    public ResponseEntity<?> updateFacility(@PathVariable Long facilityId, @RequestBody Map<String, Object> updates) {
        if (facilityId == null || facilityId <= 0 || updates == null || updates.isEmpty()) {
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        }
        SportFacility updatedFacility = facilityService.updateFacility(facilityId, updates);
        return new ResponseEntity<>(updatedFacility, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllFacilities() {
        Iterable<SportFacility> facilities = facilityService.getAllFacilities();
        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/type/{type}")
    public ResponseEntity<?> getFacilitiesByType(@PathVariable SportType type) {
        if (type == null) {
            return new ResponseEntity<>("Type cannot be null", HttpStatus.BAD_REQUEST);
        }
        Iterable<SportFacility> facilities = facilityService.getFacilitiesByType(type);
        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getFacilitiesByName(@PathVariable String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ResponseEntity<>("Name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        Iterable<SportFacility> facilities = facilityService.getFacilitiesByName(name);
        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }
}
