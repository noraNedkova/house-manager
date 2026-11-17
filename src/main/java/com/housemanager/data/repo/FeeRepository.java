package com.housemanager.data.repo;

import com.housemanager.data.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FeeRepository extends JpaRepository<Fee, Long> {
    Fee findByBuildingId(long id);
}
