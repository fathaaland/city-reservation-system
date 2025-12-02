package com.city.reservation.CityReservationSystem.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message, HttpStatus badRequest) { super(message); }
}