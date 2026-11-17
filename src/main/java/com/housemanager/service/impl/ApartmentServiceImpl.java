package com.housemanager.service.impl;

import com.housemanager.data.entity.Apartment;
import com.housemanager.data.entity.Building;
import com.housemanager.data.entity.Owner;
import com.housemanager.data.entity.Resident;
import com.housemanager.data.repo.ApartmentRepository;
import com.housemanager.data.repo.BuildingRepository;
import com.housemanager.data.repo.OwnerRepository;
import com.housemanager.data.repo.ResidentRepository;
import com.housemanager.dto.ApartmentDTO;
import com.housemanager.service.ApartmentService;
import com.housemanager.util.MapperUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final BuildingRepository buildingRepository;
    private final OwnerRepository ownerRepository;
    private final ResidentRepository residentRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<ApartmentDTO> getAllApartments() {
        return this.mapperUtil.mapList(apartmentRepository.findAll(), ApartmentDTO.class);
    }

    @Override
    public ApartmentDTO getApartmentById(long id) {
        return this.mapperUtil.map(apartmentRepository.findById(id), ApartmentDTO.class);
    }

    @Override
    public ApartmentDTO createApartment(ApartmentDTO apartmentDTO) {

        Apartment apartment = new Apartment();

        apartment.setApartmentNumber(apartmentDTO.getApartmentNumber());
        apartment.setFloor(apartmentDTO.getFloor());
        apartment.setApartmentArea(apartmentDTO.getApartmentArea());
        apartment.setNumberOfPetsUsingCommonParts(
                apartmentDTO.getNumberOfPetsUsingCommonParts());

        Building building = buildingRepository.findById(apartmentDTO.getBuildingId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Building not found with id: " + apartmentDTO.getBuildingId()));

        apartment.setBuilding(building);

        Set<Owner> owners = new HashSet<>();

        if (apartmentDTO.getOwnerIds() != null) {
            for (Long ownerId : apartmentDTO.getOwnerIds()) {

                Owner owner = ownerRepository.findById(ownerId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Owner not found with id: " + ownerId));

                owners.add(owner);
            }
        }

        apartment.setOwners(owners);

        Set<Resident> residents = new HashSet<>();

        if (apartmentDTO.getResidentIds() != null) {
            for (Long residentId : apartmentDTO.getResidentIds()) {

                Resident resident = residentRepository.findById(residentId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Resident not found with id: " + residentId));

                resident.setResidencyApartment(apartment);

                residents.add(resident);
            }
        }

        apartment.setResidents(residents);

        Apartment savedApartment = apartmentRepository.save(apartment);

        return mapperUtil.getModelMapper()
                .map(savedApartment, ApartmentDTO.class);
    }


    @Override
    public ApartmentDTO updateApartment(ApartmentDTO dto, long id) {

        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Apartment not found with id: " + id));

        apartment.setApartmentNumber(dto.getApartmentNumber());
        apartment.setFloor(dto.getFloor());
        apartment.setApartmentArea(dto.getApartmentArea());
        apartment.setNumberOfPetsUsingCommonParts(
                dto.getNumberOfPetsUsingCommonParts());

        if (dto.getOwnerIds() != null) {
            Set<Owner> owners = new HashSet<>();
            for (Long ownerId : dto.getOwnerIds()) {
                Owner owner = ownerRepository.findById(ownerId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Owner not found with id: " + ownerId));
                owners.add(owner);
            }
             apartment.setOwners(owners);
        }

        if (dto.getResidentIds() != null) {
            Set<Resident> residents = new HashSet<>();
            for (Long residentId : dto.getResidentIds()) {
                Resident resident = residentRepository.findById(residentId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Resident not found with id: " + residentId));
                residents.add(resident);
            }

            for (Resident oldResident : new HashSet<>(apartment.getResidents())) {
                if (!residents.contains(oldResident)) {
                    oldResident.setResidencyApartment(null);
                }
            }

            for (Resident resident : residents) {
                resident.setResidencyApartment(apartment);
            }
            apartment.setResidents(residents);
        }

        Apartment savedApartment = apartmentRepository.save(apartment);

        return mapperUtil.getModelMapper()
                .map(savedApartment, ApartmentDTO.class);
    }


    @Override
    public void deleteApartment(long id) {
        this.apartmentRepository.deleteById(id);
    }

    @Override
    public int getNumberOfApartmentsByBuilding(Long id){
        return this.apartmentRepository.countByBuildingId(id);
    }

    @Override
    public List<ApartmentDTO> getApartmentsByBuilding(Long id) {
        return mapperUtil.mapList(apartmentRepository.findByBuildingId(id), ApartmentDTO.class);
    }
}
