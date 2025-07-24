package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.SportFacility;
import com.city.reservation.CityReservationSystem.repository.FacilityRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public void deleteFacilityById(Long id) {

    }

    @Override
    public SportFacility updateFacility(Long facilityId, SportFacility facility) {
        return null;
    }

    @Override
    public Iterable<SportFacility> getAllFacilities() {
        return null;
    }

    @Override
    public Iterable<SportFacility> getFacilitiesByType(String type) {
        return null;
    }

    @Override
    public Iterable<SportFacility> getFacilitiesByName(String name) {
        return null;
    }
}
