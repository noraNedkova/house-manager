package com.housemanager.service;

import com.housemanager.dto.ResidentDTO;

import java.util.List;

public interface ResidentService {

    List<ResidentDTO> getAllResidents();

    ResidentDTO getResidentById(long id);

    ResidentDTO createResident(ResidentDTO resident);

    ResidentDTO updateResident(ResidentDTO resident, long id);

    void deleteResident(long id);

    List<ResidentDTO> findAllByBuildingIdOrderByName(long id);

    List<ResidentDTO> findAllByBuildingIdOrderByBirthday(long id);

    Long countByBuildingId(long id);
}