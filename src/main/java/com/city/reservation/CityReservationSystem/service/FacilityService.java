package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.SportFacility;
import com.city.reservation.CityReservationSystem.model.enums.SportType;
import com.city.reservation.CityReservationSystem.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class FacilityService implements IFacilityService {
    private final FacilityRepository facilityRepository;


    private SportFacility createFacility(SportFacility facility) {
        return SportFacility.builder()
                .name(facility.getName())
                .type(facility.getType())
                .description(facility.getDescription())
                .address(facility.getAddress())
                .contactNumber(facility.getContactNumber())
                .capacity(facility.getCapacity())
                .imageUrl(facility.getImageUrl())
                .build();
    }



    @Override
    public SportFacility addFacility(SportFacility facility) {
        try{
            SportFacility newFacility = createFacility(facility);
            return facilityRepository.save(newFacility);
        } catch (Exception e) {
            throw new RuntimeException("Error adding facility: " + e.getMessage(), e);
        }
    }

    @Override
    public SportFacility getFacilityById(Long id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + id));
    }

    @Override
    public void deleteFacilityById(Long id) {
        if (!facilityRepository.existsById(id)) {
            throw new RuntimeException("Facility not found with id: " + id);
        }
        facilityRepository.deleteById(id);
    }

    @Override
    public SportFacility updateFacility(Long facilityId, SportFacility facility) {
        if (!facilityRepository.existsById(facilityId)) {
            throw new RuntimeException("Facility not found with id: " + facilityId);
        }
        SportFacility existingFacility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + facilityId));
        existingFacility.setName(facility.getName());
        existingFacility.setType(facility.getType());
        existingFacility.setDescription(facility.getDescription());
        existingFacility.setAddress(facility.getAddress());
        existingFacility.setContactNumber(facility.getContactNumber());
        existingFacility.setCapacity(facility.getCapacity());
        existingFacility.setImageUrl(facility.getImageUrl());
        return facilityRepository.save(existingFacility);
    }

    @Override
    public Iterable<SportFacility> getAllFacilities() {
        return facilityRepository.findAll();
    }


    @Override
    public Iterable<SportFacility> getFacilitiesByType(SportType type) {
        if (facilityRepository.existsByType(type)) {
            return facilityRepository.findByType(type);
        } else {
            throw new RuntimeException("No facilities found for type: " + type);
        }
    }

    @Override
    public Iterable<SportFacility> getFacilitiesByName(String name) {
        if (facilityRepository.existsByName(name)) {
            return Collections.singleton(facilityRepository.findByName(name));
        } else {
            throw new RuntimeException("No facilities found with name: " + name);
        }
    }
}
