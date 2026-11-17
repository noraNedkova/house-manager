package com.housemanager.service;

import com.housemanager.dto.BuildingDTO;

import java.util.List;

public interface BuildingService {

    List<BuildingDTO> getAllBuildings();

    BuildingDTO getBuildingById(long id);

    BuildingDTO createBuilding(BuildingDTO building);

    BuildingDTO updateBuilding(BuildingDTO building, long id);

    void deleteBuilding(long id);

    List<BuildingDTO> findBuildingsByCompany(long id);
}
