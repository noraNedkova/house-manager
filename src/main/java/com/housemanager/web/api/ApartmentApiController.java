package com.housemanager.web.api;

import com.housemanager.dto.ApartmentDTO;
import com.housemanager.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apartments")
public class ApartmentApiController {
    private final ApartmentService apartmentService;

    @GetMapping
    public List<ApartmentDTO> getApartments() {
        return apartmentService.getAllApartments();
    }

    @GetMapping("/{id}")
    public ApartmentDTO getApartment(@PathVariable Long id) {
        return apartmentService.getApartmentById(id);
    }

    @PostMapping
    public ApartmentDTO createApartment(@RequestBody ApartmentDTO apartment) {
        return apartmentService.createApartment(apartment);
    }

    @PutMapping("/{id}")
    public ApartmentDTO updateApartment(@RequestBody ApartmentDTO apartment,@PathVariable Long id) {
        return apartmentService.updateApartment(apartment, id);
    }

    @DeleteMapping("/{id}")
    public void deleteApartment(@PathVariable long id) {
        apartmentService.deleteApartment(id);
    }

    @GetMapping("/number_of_apartments_by_building/{id}")
    public int getNumberOfApartmentsByBuilding(@PathVariable long id) {
        return apartmentService.getNumberOfApartmentsByBuilding(id);
    }

    @GetMapping("/apartments_by_building/{id}")
    public List<ApartmentDTO> getApartmentsByBuilding(@PathVariable long id) {
        return apartmentService.getApartmentsByBuilding(id);
    }
}
