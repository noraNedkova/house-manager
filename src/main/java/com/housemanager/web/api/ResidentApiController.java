package com.housemanager.web.api;

import com.housemanager.dto.ResidentDTO;
import com.housemanager.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/residents")
public class ResidentApiController {
    private final ResidentService residentService;

    @GetMapping
    public List<ResidentDTO> getResidents() {
        return residentService.getAllResidents();
    }

    @GetMapping("/{id}")
    public ResidentDTO getResident(@PathVariable Long id) {
        return residentService.getResidentById(id);
    }

    @PostMapping
    public ResidentDTO createResident(@RequestBody ResidentDTO resident) {
        return residentService.createResident(resident);
    }

    @PutMapping("/{id}")
    public ResidentDTO updateResident(@RequestBody ResidentDTO resident,@PathVariable Long id) {
        return residentService.updateResident(resident, id);
    }

    @DeleteMapping("/{id}")
    public void deleteResident(@PathVariable long id) {
        residentService.deleteResident(id);
    }

    @GetMapping("/get_residents_by_building_sorted_names/{id}")
    public List<ResidentDTO> findAllByBuildingIdOrderByName(@PathVariable long id) {
        return residentService.findAllByBuildingIdOrderByName(id);
    }

    @GetMapping("/get_residents_by_building_sorted_birthday/{id}")
    public List<ResidentDTO> findAllByBuildingIdOrderByBirthday(@PathVariable long id) {
        return residentService.findAllByBuildingIdOrderByBirthday(id);
    }

    @GetMapping("/count_residents_by_building/{id}")
    public Long countByBuilding(@PathVariable long id) {
        return residentService.countByBuildingId(id);
    }
}
