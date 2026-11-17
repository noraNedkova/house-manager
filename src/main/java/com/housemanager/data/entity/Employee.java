package com.housemanager.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@DiscriminatorValue("employee")
public class Employee extends Person {
    @ManyToOne
    @NotNull
    private Company company;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private Set<Building> buildings;
}
