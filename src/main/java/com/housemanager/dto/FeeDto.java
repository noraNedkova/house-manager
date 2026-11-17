package com.housemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeeDto {

    private Long buildingId;

    private BigDecimal pricePerSquareMeter;

    private BigDecimal elevatorFee;

    private BigDecimal petFee;
}
