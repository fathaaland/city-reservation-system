package com.city.reservation.CityReservationSystem.service;

import com.city.reservation.CityReservationSystem.model.entity.SportFacility;
import com.city.reservation.CityReservationSystem.model.enums.SportType;

import java.util.Map;

public interface IFacilityService {
    SportFacility addFacility(SportFacility facility);
    SportFacility getFacilityById(Long id);
    void deleteFacilityById(Long id);
    SportFacility updateFacility(Long facilityId, Map<String, Object> updates);
    Iterable<SportFacility> getAllFacilities();
    Iterable<SportFacility> getFacilitiesByType(SportType type);
    Iterable<SportFacility> getFacilitiesByName(String name);

}
