package com.housemanager.data.repo;

import com.housemanager.data.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
    Long countByResidencyApartmentBuildingId(Long buildingId);
    List<Resident> findByResidencyApartmentBuildingId(Long buildingId);
    List<Resident> findAllByResidencyApartmentBuildingIdOrderByFirstNameAsc(Long buildingId);
    List<Resident> findAllByResidencyApartmentBuildingIdOrderByFirstNameDesc(Long buildingId);
    List<Resident> findAllByResidencyApartmentBuildingIdOrderByBirthdayAsc(Long buildingId);
    List<Resident> findAllByResidencyApartmentBuildingIdOrderByBirthdayDesc(Long buildingId);
    List<Resident> findByFirstNameAndResidencyApartmentBuildingId(String firstName, Long buildingId);
    List<Resident> findByBirthdayAndResidencyApartmentBuildingId(LocalDate birthday, Long buildingId);
}
