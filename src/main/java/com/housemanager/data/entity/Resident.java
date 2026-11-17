package com.housemanager.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("resident")
public class Resident extends Person {

    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment residencyApartment;

    @Column(name="uses_elevator")
    boolean usesElevator;
}
