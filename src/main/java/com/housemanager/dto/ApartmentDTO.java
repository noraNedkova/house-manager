package com.housemanager.dto;

import com.housemanager.data.entity.Owner;
import com.housemanager.data.entity.Payment;
import com.housemanager.data.entity.Resident;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApartmentDTO {

    private int apartmentNumber;

    private int floor;

    private BigDecimal apartmentArea;

    private int numberOfPetsUsingCommonParts;

    private Long buildingId;

    private Set<Long> ownerIds;

    private Set<Long> residentIds;
}
