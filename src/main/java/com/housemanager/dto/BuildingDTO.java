package com.housemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BuildingDTO {

    private String buildingName;

    private String buildingNumber;

    private String address;

    private int numberOfFloors;

    private double buildingArea;

    private double commonArea;

    private Long companyId;

    private Set<ApartmentDTO> apartments;

    private Long employeeId;

    private Long feeId;
}
