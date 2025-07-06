package com.example.sportreservation.model.entity;

import com.city.reservation.CityReservationSystem.model.entity.Reservation;
import com.city.reservation.CityReservationSystem.model.enums.SportType;
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

    @OneToMany(mappedBy = "sportFacility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}
