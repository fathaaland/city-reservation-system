package com.city.reservation.CityReservationSystem.model.entity;

import com.city.reservation.CityReservationSystem.model.enums.SportType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sport_facilities")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SportFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SportType type;

    @Column
    private String location;

    @Column(length = 500)
    private String description;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @Column(nullable = false)
    private int capacity;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "sportFacility", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;

}
