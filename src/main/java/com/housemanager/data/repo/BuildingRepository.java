package com.housemanager.data.repo;

import com.housemanager.data.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository  extends JpaRepository<Building, Long> {
    List<Building> findAllByEmployeeCompanyId(Long companyId);
    long countByEmployeeId(Long id);
    List<Building> findAllByEmployeeId(Long employeeId);
    List<Building> findAllByCompanyId(Long companyId);
}
