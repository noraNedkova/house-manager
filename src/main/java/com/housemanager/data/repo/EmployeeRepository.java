package com.housemanager.data.repo;

import com.housemanager.data.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByCompanyIdOrderByFirstNameAsc(Long companyId);
    List<Employee> findAllByCompanyId(Long companyId);
    List<Employee> findAllByCompanyIdAndIdNot(Long companyId, Long excludedId);
}
