package com.city.reservation.CityReservationSystem.exceptions;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) { super(message); }
}