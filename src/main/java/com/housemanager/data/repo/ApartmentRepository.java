package com.housemanager.data.repo;

import com.housemanager.data.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    List<Apartment> findByBuildingId(Long id);
    int countByBuildingId(Long id);
}
