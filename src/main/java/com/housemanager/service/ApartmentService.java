package com.housemanager.service;

import com.housemanager.dto.ApartmentDTO;

import java.util.List;

public interface ApartmentService {

    List<ApartmentDTO> getAllApartments();

    ApartmentDTO getApartmentById(long id);

    ApartmentDTO createApartment(ApartmentDTO apartment);

    ApartmentDTO updateApartment(ApartmentDTO apartment, long id);

    void deleteApartment(long id);

    int getNumberOfApartmentsByBuilding(Long id);

    List<ApartmentDTO> getApartmentsByBuilding(Long id);
}