package com.housemanager.service.impl;

import com.housemanager.data.entity.Building;
import com.housemanager.data.entity.Company;
import com.housemanager.data.repo.BuildingRepository;
import com.housemanager.data.repo.CompanyRepository;
import com.housemanager.data.repo.EmployeeRepository;
import com.housemanager.dto.BuildingDTO;
import com.housemanager.dto.EmployeeDTO;
import com.housemanager.service.BuildingService;
import com.housemanager.service.EmployeeService;
import com.housemanager.util.MapperUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;
    private final MapperUtil mapperUtil;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    @Override
    public List<BuildingDTO> getAllBuildings() {
        return this.mapperUtil.mapList(buildingRepository.findAll(), BuildingDTO.class);
    }

    @Override
    public BuildingDTO getBuildingById(long id) {
        return this.mapperUtil.map(buildingRepository.findById(id), BuildingDTO.class);
    }

    @Override
    public BuildingDTO createBuilding(BuildingDTO buildingDTO) {

        Building newBuilding = new Building();

        newBuilding.setBuildingName(buildingDTO.getBuildingName());
        newBuilding.setBuildingNumber(buildingDTO.getBuildingNumber());
        newBuilding.setAddress(buildingDTO.getAddress());
        newBuilding.setNumberOfFloors(buildingDTO.getNumberOfFloors());
        newBuilding.setBuildingArea(buildingDTO.getBuildingArea());
        newBuilding.setCommonArea(buildingDTO.getCommonArea());

        assignCompanyAndEmployee(newBuilding, buildingDTO.getCompanyId());

        Building savedBuilding = buildingRepository.save(newBuilding);

        return mapperUtil.map(savedBuilding, BuildingDTO.class);
    }

    @Override
    public BuildingDTO updateBuilding(BuildingDTO buildingDTO, long id) {

        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Building not found with id: " + id));

        Company oldCompany = building.getCompany();

        building.setBuildingName(buildingDTO.getBuildingName());
        building.setBuildingNumber(buildingDTO.getBuildingNumber());
        building.setAddress(buildingDTO.getAddress());
        building.setNumberOfFloors(buildingDTO.getNumberOfFloors());
        building.setBuildingArea(buildingDTO.getBuildingArea());
        building.setCommonArea(buildingDTO.getCommonArea());

        if (!oldCompany.getId().equals(buildingDTO.getCompanyId())) {
            assignCompanyAndEmployee(building, buildingDTO.getCompanyId());
        }

        Building updatedBuilding = buildingRepository.save(building);

        return mapperUtil.map(updatedBuilding, BuildingDTO.class);
    }

    private void assignCompanyAndEmployee(Building building, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));

        EmployeeDTO chosen = employeeService.findEmployeeWithFewestBuildings(company.getId());

        building.setCompany(company);
        building.setEmployee(employeeRepository.getReferenceById(chosen.getId()));
    }

    @Override
    public void deleteBuilding(long id) {
        this.buildingRepository.deleteById(id);
    }

    @Override
    public List<BuildingDTO> findBuildingsByCompany(long id){
        return this.mapperUtil.mapList(buildingRepository.findAllByEmployeeCompanyId(id), BuildingDTO.class);
    }

}
