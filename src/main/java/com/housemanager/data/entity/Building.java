
package com.housemanager.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "buildings")
public class Building extends BaseEntity {

    @Column(name = "building_name")
    @NotBlank(message = "Building name cannot be blank")
    @Size(min = 2, max = 100,
            message = "Building name must be between 2 and 100 characters")
    private String buildingName;

    @Column(name = "building_number")
    @NotBlank(message = "Building number cannot be blank")
    @Size(max = 20,
            message = "Building number cannot exceed 20 characters")
    private String buildingNumber;

    @Column
    @NotBlank(message = "Address cannot be blank")
    @Size(min = 5, max = 255,
            message = "Address must be between 5 and 255 characters")
    private String address;

    @Column(name = "number_of_floors")
    @Positive(message = "Building must have at least 1 floor")
    @NotNull(message = "Number of floors cannot be null")
    @Max(value = 200, message = "Number of floors cannot exceed 200")
    private int numberOfFloors;

    @Column(name = "building_area")
    @Positive(message = "Building area must be greater than 0")
    @NotNull(message = "Building area cannot be null")
    @Max(value = 1000000, message = "Building area is too large")
    private double buildingArea;

    @Column(name = "common_area")
    @PositiveOrZero(message = "Common area cannot be negative")
    @NotNull(message = "Common area cannot be null")
    private double commonArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "A building must have a contract with a company")
    private Company company;

    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
    private Set<Apartment> apartments;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Employee employee;

    @OneToOne(mappedBy = "building", fetch = FetchType.LAZY)
    private Fee fee;
}
