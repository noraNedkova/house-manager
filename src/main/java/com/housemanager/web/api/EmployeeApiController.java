package com.housemanager.web.api;

import com.housemanager.dto.EmployeeDTO;
import com.housemanager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeApiController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDTO> getEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployee(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employee,@PathVariable Long id) {
        return employeeService.updateEmployee(employee, id);
    }

    @DeleteMapping("/remove_employee/{id}")
    public void removeEmployee(@PathVariable long id) {
        employeeService.removeEmployee(id);
    }

    @GetMapping("/get_employees_by_company/{id}")
    public List<EmployeeDTO> getEmployeesByCompanyId(@PathVariable long id) {
        return employeeService.findAllByCompanyIdOrderByFirstNameAsc(id);
    }

    @GetMapping("/get_employees_by_company_sort_buildings/{id}")
    public List<EmployeeDTO> findAllByCompanyIdOrderByBuildings(@PathVariable long id) {
        return employeeService.findAllByCompanyIdOrderByBuildings(id);
    }
}
