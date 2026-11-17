package com.housemanager.service.impl;

import com.housemanager.data.entity.Building;
import com.housemanager.data.entity.Company;
import com.housemanager.data.entity.Employee;
import com.housemanager.data.repo.BuildingRepository;
import com.housemanager.data.repo.CompanyRepository;
import com.housemanager.data.repo.EmployeeRepository;
import com.housemanager.dto.BuildingDTO;
import com.housemanager.dto.CompanyDTO;
import com.housemanager.dto.EmployeeDTO;
import com.housemanager.service.EmployeeService;
import com.housemanager.util.MapperUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final BuildingRepository buildingRepository;
    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return this.mapperUtil.mapList(employeeRepository.findAll(), EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getEmployeeById(long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        return mapperUtil.map(employee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setBirthday(employeeDTO.getBirthday());

        Company company = companyRepository.findById(employeeDTO.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Company not found with id: " + employeeDTO.getCompanyId()));

        employee.setCompany(company);

        Employee savedEmployee = employeeRepository.save(employee);

        return mapperUtil.getModelMapper().map(savedEmployee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found with id: " + id));

        mapperUtil.getModelMapper().map(employeeDTO, employee);

        Employee updatedEmployee = employeeRepository.save(employee);

        return mapperUtil.map(updatedEmployee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> findAllByCompanyIdOrderByFirstNameAsc(long id) {
        return this.mapperUtil.mapList
                (employeeRepository.findAllByCompanyIdOrderByFirstNameAsc(id), EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> findAllByCompanyIdOrderByBuildings(long id) {
        List<Employee> employees = employeeRepository.findAllByCompanyId(id);

        employees.sort((first, second) -> Long.compare(
                buildingRepository.countByEmployeeId(second.getId()),
                buildingRepository.countByEmployeeId(first.getId())));

        return mapperUtil.mapList(employees, EmployeeDTO.class);
    }

    private Employee pickEmployeeWithFewestBuildings(List<Employee> employees) {
        if (employees.isEmpty()) {
            throw new IllegalStateException("There is no employee available to manage the building");
        }

        Employee leastBusy = employees.get(0);
        long leastBusyCount = buildingRepository.countByEmployeeId(leastBusy.getId());

        for (Employee candidate : employees) {
            long count = buildingRepository.countByEmployeeId(candidate.getId());

            if (count < leastBusyCount) {
                leastBusy = candidate;
                leastBusyCount = count;
            }
        }
        return leastBusy;
    }

    @Override
    public EmployeeDTO findEmployeeWithFewestBuildings(Long companyId) {
        List<Employee> candidates = employeeRepository.findAllByCompanyId(companyId);
        return mapperUtil.map(pickEmployeeWithFewestBuildings(candidates), EmployeeDTO.class);
    }


    private void reassignBuildings(List<Building> buildings, Employee leavingEmployee) {
        List<Employee> colleagues = employeeRepository
                .findAllByCompanyIdAndIdNot(leavingEmployee.getCompany().getId(), leavingEmployee.getId());

        if (colleagues.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot remove the last employee of a company that still has buildings");
        }

        for (Building building : buildings) {
            Employee leastBusy = pickEmployeeWithFewestBuildings(colleagues);

            building.setEmployee(leastBusy);
            buildingRepository.saveAndFlush(building);
        }
    }

    @Override
    public void removeEmployee(long id) {
        Employee employeeLeaving = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        List<Building> buildings = buildingRepository.findAllByEmployeeId(id);

        if (!buildings.isEmpty()) {
            reassignBuildings(buildings, employeeLeaving);
        }

        employeeRepository.deleteById(id);
    }

}
