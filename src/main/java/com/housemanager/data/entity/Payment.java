package com.housemanager.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "payment")
public class Payment  extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Apartment apartment;

    @Column
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate period;

    @Column @NotNull
    private BigDecimal amount;

    @Column
    private boolean paid;

    @Column(name = "payment_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate paymentDate;
}
