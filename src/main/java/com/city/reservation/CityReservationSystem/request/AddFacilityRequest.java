package com.city.reservation.CityReservationSystem.request;

import lombok.Data;

@Data
public class AddFacilityRequest {
    private String name;
    private String type;
    private String description;
    private String address;
    private String contactNumber;
    private int capacity;
    private String imageUrl;
}
