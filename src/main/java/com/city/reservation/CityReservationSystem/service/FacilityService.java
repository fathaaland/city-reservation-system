package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.exceptions.BadRequestException;
import com.city.reservation.CityReservationSystem.exceptions.EntityNotFoundException;
import com.city.reservation.CityReservationSystem.model.entity.SportFacility;
import com.city.reservation.CityReservationSystem.model.enums.SportType;
import com.city.reservation.CityReservationSystem.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

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
            throw new BadRequestException("Facility cannot be null.", HttpStatus.BAD_REQUEST);
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
    public SportFacility updateFacility(Long facilityId, Map<String, Object> updates) {
        SportFacility existingFacility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility not found with id: " + facilityId));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> existingFacility.setName((String) value);
                case "type" -> existingFacility.setType((SportType) value);
                case "description" -> existingFacility.setDescription((String) value);
                case "address" -> existingFacility.setAddress((String) value);
                case "contactNumber" -> existingFacility.setContactNumber((String) value);
                case "capacity" -> existingFacility.setCapacity(Integer.parseInt(value.toString()));
                case "imageUrl" -> existingFacility.setImageUrl((String) value);
                default -> throw new IllegalArgumentException("Unknown field: " + key);
            }
        });

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
            throw new BadRequestException("No facilities found for type: " + type, HttpStatus.BAD_REQUEST);
        }
        return facilities;
    }

    @Override
    public Iterable<SportFacility> getFacilitiesByName(String name) {
        if (!facilityRepository.existsByName(name)) {
            throw new BadRequestException("No facilities found with name: " + name, HttpStatus.BAD_REQUEST);
        }
        return Collections.singleton(facilityRepository.findByName(name));
    }


}
