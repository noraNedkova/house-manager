package com.housemanager.web.api;

import com.housemanager.dto.BuildingDTO;
import com.housemanager.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buildings")
public class BuildingApiController {
    private final BuildingService buildingService;

    @GetMapping
    public List<BuildingDTO> getBuildings() {
        return buildingService.getAllBuildings();
    }

    @GetMapping("/{id}")
    public BuildingDTO getBuilding(@PathVariable Long id) {
        return buildingService.getBuildingById(id);
    }

    @PostMapping
    public BuildingDTO createBuilding(@RequestBody BuildingDTO building) {
        return buildingService.createBuilding(building);
    }

    @PutMapping("/{id}")
    public BuildingDTO updateBuilding(@RequestBody BuildingDTO building,@PathVariable Long id) {
        return buildingService.updateBuilding(building, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBuilding(@PathVariable long id) {
        buildingService.deleteBuilding(id);
    }

    @GetMapping("/buildings_by_company/{id}")
    public List<BuildingDTO> getBuildingsByCompany(@PathVariable long id) {
        return buildingService.findBuildingsByCompany(id);
    }
}
