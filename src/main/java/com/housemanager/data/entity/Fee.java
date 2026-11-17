package com.housemanager.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "fee")
public class Fee extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Building building;

    @NotNull
    @PositiveOrZero
    @Column(name = "price_per_sqr_meter")
    private BigDecimal pricePerSquareMeter;

    @NotNull
    @PositiveOrZero
    @Column(name = "elevator_fee")
    private BigDecimal elevatorFee;

    @NotNull
    @PositiveOrZero
    @Column(name = "pet_fee")
    private BigDecimal petFee;

}
