package com.housemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDTO {

    private ApartmentDTO apartment;

    private LocalDate period;

    private BigDecimal amount;

    private boolean paid;

    private LocalDate paymentDate;
}
