package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.exceptions.BadRequestException;
import com.city.reservation.CityReservationSystem.exceptions.EntityNotFoundException;
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
        if (facility == null) {
            throw new BadRequestException("Facility cannot be null.");
        }

        SportFacility newFacility = createFacility(facility);
        return facilityRepository.save(newFacility);
    }

    @Override
    public SportFacility getFacilityById(Long id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Facility not found with id: " + id));
    }

    @Override
    public void deleteFacilityById(Long id) {
        if (!facilityRepository.existsById(id)) {
            throw new EntityNotFoundException("Facility not found with id: " + id);
        }
        facilityRepository.deleteById(id);
    }

    @Override
    public SportFacility updateFacility(Long facilityId, SportFacility facility) {
        SportFacility existingFacility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility not found with id: " + facilityId));

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
        var facilities = facilityRepository.findByType(type);
        if (facilities == null || !facilities.iterator().hasNext()) {
            throw new BadRequestException("No facilities found for type: " + type);
        }
        return facilities;
    }

    @Override
    public Iterable<SportFacility> getFacilitiesByName(String name) {
        if (!facilityRepository.existsByName(name)) {
            throw new BadRequestException("No facilities found with name: " + name);
        }
        return Collections.singleton(facilityRepository.findByName(name));
    }
}
