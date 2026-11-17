package com.housemanager.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "companies")
public class Company extends BaseEntity {

    @NotNull
    @Column(name = "company_name")
    private String companyName;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<Employee> employees;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<Building> buildings;
}
