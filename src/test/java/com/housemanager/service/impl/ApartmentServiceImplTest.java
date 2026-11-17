package com.housemanager.service.impl;

import com.housemanager.data.entity.Apartment;
import com.housemanager.data.entity.Building;
import com.housemanager.data.entity.Owner;
import com.housemanager.data.repo.ApartmentRepository;
import com.housemanager.data.repo.BuildingRepository;
import com.housemanager.data.repo.OwnerRepository;
import com.housemanager.data.repo.ResidentRepository;
import com.housemanager.dto.ApartmentDTO;
import com.housemanager.util.MapperUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class ApartmentServiceImplTest {

    @MockitoBean
    private ApartmentRepository apartmentRepository;

    @MockitoBean
    private BuildingRepository buildingRepository;

    @MockitoBean
    private OwnerRepository ownerRepository;

    @MockitoBean
    private ResidentRepository residentRepository;

    @Autowired
    private ApartmentServiceImpl apartmentServiceImpl;

    @Autowired
    private MapperUtil mapperUtil;

    private Apartment apartment;
    private Building building;

    @BeforeEach
    public void init() {
        building = new Building();

        apartment = new Apartment();
        apartment.setApartmentNumber(101);
        apartment.setFloor(1);
        apartment.setApartmentArea(new BigDecimal("85.50"));
        apartment.setNumberOfPetsUsingCommonParts(1);
        apartment.setBuilding(building);
    }

    @Test
    void getAllApartmentsTest() {
        Apartment apartment2 = new Apartment();
        apartment2.setApartmentNumber(102);
        apartment2.setFloor(1);
        apartment2.setApartmentArea(new BigDecimal("92.00"));
        apartment2.setBuilding(building);

        List<Apartment> apartments = Arrays.asList(apartment, apartment2);
        List<ApartmentDTO> expected = mapperUtil.mapList(apartments, ApartmentDTO.class);

        given(apartmentRepository.findAll()).willReturn(apartments);

        List<ApartmentDTO> actual = apartmentServiceImpl.getAllApartments();

        assertThat(actual).hasSize(2);
        assertIterableEquals(
                expected.stream().map(ApartmentDTO::getApartmentNumber).toList(),
                actual.stream().map(ApartmentDTO::getApartmentNumber).toList());
    }

    @Test
    void getApartmentByIdTest() {
        given(apartmentRepository.findById(1L)).willReturn(Optional.of(apartment));

        ApartmentDTO result = apartmentServiceImpl.getApartmentById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getApartmentNumber()).isEqualTo(101);
    }

    @Test
    void createApartmentTest() {
        ApartmentDTO dto = new ApartmentDTO();
        dto.setApartmentNumber(101);
        dto.setFloor(1);
        dto.setApartmentArea(new BigDecimal("85.50"));
        dto.setNumberOfPetsUsingCommonParts(1);
        dto.setBuildingId(1L);

        given(buildingRepository.findById(1L)).willReturn(Optional.of(building));
        given(apartmentRepository.save(any(Apartment.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ApartmentDTO result = apartmentServiceImpl.createApartment(dto);

        assertThat(result.getApartmentNumber()).isEqualTo(101);
        assertThat(result.getFloor()).isEqualTo(1);
    }

    @Test
    void createApartmentWithOwnersTest() {
        Owner owner = new Owner();

        ApartmentDTO dto = new ApartmentDTO();
        dto.setApartmentNumber(101);
        dto.setApartmentArea(new BigDecimal("85.50"));
        dto.setBuildingId(1L);
        dto.setOwnerIds(Set.of(6L));

        given(buildingRepository.findById(1L)).willReturn(Optional.of(building));
        given(ownerRepository.findById(6L)).willReturn(Optional.of(owner));
        given(apartmentRepository.save(any(Apartment.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ApartmentDTO result = apartmentServiceImpl.createApartment(dto);

        assertThat(result).isNotNull();
    }

    @Test
    void createApartmentBuildingNotFoundTest() {
        ApartmentDTO dto = new ApartmentDTO();
        dto.setBuildingId(99L);

        given(buildingRepository.findById(99L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> apartmentServiceImpl.createApartment(dto));
    }

    @Test
    void createApartmentOwnerNotFoundTest() {
        ApartmentDTO dto = new ApartmentDTO();
        dto.setBuildingId(1L);
        dto.setOwnerIds(Set.of(99L));

        given(buildingRepository.findById(1L)).willReturn(Optional.of(building));
        given(ownerRepository.findById(99L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> apartmentServiceImpl.createApartment(dto));
    }

    @Test
    void updateApartmentTest() {
        ApartmentDTO dto = new ApartmentDTO();
        dto.setApartmentNumber(105);
        dto.setFloor(2);
        dto.setApartmentArea(new BigDecimal("90.00"));
        dto.setNumberOfPetsUsingCommonParts(2);

        given(apartmentRepository.findById(1L)).willReturn(Optional.of(apartment));
        given(apartmentRepository.save(any(Apartment.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ApartmentDTO result = apartmentServiceImpl.updateApartment(dto, 1L);

        assertThat(result.getApartmentNumber()).isEqualTo(105);
        assertThat(result.getFloor()).isEqualTo(2);
        assertThat(result.getApartmentArea()).isEqualByComparingTo("90.00");
        assertThat(result.getNumberOfPetsUsingCommonParts()).isEqualTo(2);
    }

    @Test
    void updateApartmentNotFoundTest() {
        given(apartmentRepository.findById(99L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> apartmentServiceImpl.updateApartment(new ApartmentDTO(), 99L));
    }

    @Test
    void getNumberOfApartmentsByBuildingTest() {
        given(apartmentRepository.countByBuildingId(1L)).willReturn(4);

        assertThat(apartmentServiceImpl.getNumberOfApartmentsByBuilding(1L)).isEqualTo(4);
    }

    @Test
    void getApartmentsByBuildingTest() {
        given(apartmentRepository.findByBuildingId(1L)).willReturn(List.of(apartment));

        List<ApartmentDTO> result = apartmentServiceImpl.getApartmentsByBuilding(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getApartmentNumber()).isEqualTo(101);
    }
}