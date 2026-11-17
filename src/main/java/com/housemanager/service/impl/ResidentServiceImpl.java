package com.housemanager.service.impl;

import com.housemanager.data.entity.Resident;
import com.housemanager.data.repo.ResidentRepository;
import com.housemanager.dto.EmployeeDTO;
import com.housemanager.dto.ResidentDTO;
import com.housemanager.service.ResidentService;
import com.housemanager.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository residentRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<ResidentDTO> getAllResidents() {
        return this.mapperUtil.mapList(residentRepository.findAll(), ResidentDTO.class);
    }

    @Override
    public ResidentDTO getResidentById(long id) {
        return this.mapperUtil.map(residentRepository.findById(id), ResidentDTO.class);
    }

    @Override
    public ResidentDTO createResident(ResidentDTO resident) {

        return mapperUtil.getModelMapper()
                .map(this.residentRepository
                        .save(mapperUtil.getModelMapper()
                                .map(resident, Resident.class)), ResidentDTO.class);
    }

    @Override
    public ResidentDTO updateResident(ResidentDTO residentDTO, long id) {

        Resident resident = residentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Resident not found with id: " + id));

        mapperUtil.getModelMapper().map(residentDTO, resident);

        Resident updatedResident = residentRepository.save(resident);

        return mapperUtil.map(updatedResident, ResidentDTO.class);
    }

    @Override
    public void deleteResident(long id) {
        this.residentRepository.deleteById(id);
    }

    @Override
    public List<ResidentDTO> findAllByBuildingIdOrderByName(long id) {
        return this.mapperUtil.mapList
                (residentRepository.findAllByResidencyApartmentBuildingIdOrderByFirstNameAsc(id), ResidentDTO.class);
    }

    @Override
    public List<ResidentDTO> findAllByBuildingIdOrderByBirthday(long id) {
        return this.mapperUtil.mapList
                (residentRepository.findAllByResidencyApartmentBuildingIdOrderByBirthdayAsc(id), ResidentDTO.class);
    }

    @Override
    public Long countByBuildingId(long id) {
        return residentRepository.countByResidencyApartmentBuildingId(id);
    }
}
