package com.housemanager.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="d_type")
@DiscriminatorValue("person")
public class Person extends BaseEntity {

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthday;
}
