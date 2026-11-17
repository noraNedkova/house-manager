package com.housemanager.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "apartment")
public class Apartment extends BaseEntity {

    @Column(name = "apartment_number")
    @NotNull
    @Positive(message = "Apartment number must be greater than 0")
    @Max(value = 999, message = "Apartment number cannot exceed 999")
    private int apartmentNumber;

    @Column
    @PositiveOrZero(message = "Floor must be greater than or equal to 0")
    @Max(value = 999, message = "Floor cannot exceed 999")
    private int floor;

    @Column(name = "apartment_area")
    @NotNull(message = "Apartment area cannot be null")
    @DecimalMin(value = "10.00",
            message = "Apartment area must be at least 10 square meters")
    @Digits(integer = 6, fraction = 2,
            message = "Apartment area must have up to 6 integer digits and 2 decimal places")
    private BigDecimal apartmentArea;

    @Column(name = "pets_using_common_parts")
    @PositiveOrZero(message = "Number of pets cannot be negative")
    @NotNull
    private int numberOfPetsUsingCommonParts;

    @ManyToOne
    @NotNull(message = "Apartment must belong to a building")
    private Building building;

    @ManyToMany
    private Set<Owner> owners;

    @OneToMany(mappedBy = "residencyApartment", fetch = FetchType.LAZY)
    private Set<Resident> residents;

    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY)
    private Set<Payment> payment;
}
