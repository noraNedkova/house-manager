package com.housemanager.data.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@DiscriminatorValue("owner")
public class Owner extends Person {

    @ManyToMany(mappedBy = "owners", fetch = FetchType.LAZY)
    private Set<Apartment> ownedApartments;
}
