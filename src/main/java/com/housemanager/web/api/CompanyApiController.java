package com.housemanager.web.api;

import com.housemanager.dto.CompanyDTO;
import com.housemanager.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyApiController {
    private final CompanyService companyService;

    @GetMapping
    public List<CompanyDTO> getCompanies() {
        return companyService.getCompaniesSortedByRevenue();
    }

    @GetMapping("/{id}")
    public CompanyDTO getCompany(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @PostMapping
    public CompanyDTO createCompany(@RequestBody CompanyDTO company) {
        return companyService.createCompany(company);
    }

    @PutMapping("/{id}")
    public CompanyDTO updateCompany(@RequestBody CompanyDTO company,@PathVariable Long id) {
        return companyService.updateCompany(company, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companyService.deleteCompany(id);
    }
}
