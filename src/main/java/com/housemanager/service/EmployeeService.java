package com.housemanager.service;

import com.housemanager.data.entity.Building;
import com.housemanager.data.entity.Employee;
import com.housemanager.dto.BuildingDTO;
import com.housemanager.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(long id);

    EmployeeDTO createEmployee(EmployeeDTO employee);

    EmployeeDTO updateEmployee(EmployeeDTO employee, long id);

    List<EmployeeDTO> findAllByCompanyIdOrderByFirstNameAsc(long id);

    List<EmployeeDTO> findAllByCompanyIdOrderByBuildings(long id);

    EmployeeDTO findEmployeeWithFewestBuildings(Long companyId);

    void removeEmployee(long id);

}
