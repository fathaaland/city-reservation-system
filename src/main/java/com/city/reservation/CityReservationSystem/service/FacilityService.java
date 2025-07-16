package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.SportFacility;
import com.city.reservation.CityReservationSystem.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityService implements IFacilityService {
    private final FacilityRepository facilityRepository;


//    private Product createProduct(AddProductRequest request, Category category) {
//        return new Product(
//                request.getName(),
//                request.getBrand(),
//                request.getDescription(),
//                request.getPrice(),
//                request.getInventory(),
//                category
//        );
//    }

    private SportFacility createFacility(SportFacility facility) {
        return new SportFacility(
                facility.getName(),
                facility.getType(),
                facility.getDescription(),
                facility.getAddress(),
                facility.getContactNumber(),
                facility.getCapacity(),
                facility.getImageUrl()
        );
    }


    @Override
    public SportFacility addFacility(SportFacility facility) {
        return null;
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
