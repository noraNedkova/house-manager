package com.housemanager.service.impl;

import com.housemanager.data.entity.Company;
import com.housemanager.data.repo.BuildingRepository;
import com.housemanager.data.repo.CompanyRepository;
import com.housemanager.data.repo.EmployeeRepository;
import com.housemanager.dto.CompanyDTO;
import com.housemanager.service.CompanyService;
import com.housemanager.service.PaymentService;
import com.housemanager.util.MapperUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final BuildingRepository buildingRepository;
    private final EmployeeRepository employeeRepository;
    private final PaymentService paymentService;
    private final MapperUtil mapperUtil;

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return this.mapperUtil.mapList(companyRepository.findAll(), CompanyDTO.class);
    }

    @Override
    public CompanyDTO getCompanyById(long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));
        return mapperUtil.map(company, CompanyDTO.class);
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO company) {
        Company newCompany = mapperUtil.getModelMapper().map(company, Company.class);
        newCompany.setId(null);

        return mapperUtil.getModelMapper()
                .map(this.companyRepository.save(newCompany), CompanyDTO.class);
    }

    @Override
    public CompanyDTO updateCompany(CompanyDTO companyDTO, long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));

        company.setCompanyName(companyDTO.getCompanyName());

        return mapperUtil.map(companyRepository.save(company), CompanyDTO.class);
    }

    @Override
    public void deleteCompany(long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));

        if (!buildingRepository.findAllByCompanyId(id).isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete a company that still has buildings under contract");
        }
        if (!employeeRepository.findAllByCompanyId(id).isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete a company that still has employees");
        }

        companyRepository.delete(company);
    }

    @Override
    public List<CompanyDTO> getCompaniesSortedByRevenue() {
        List<Company> companies = companyRepository.findAll();

        companies.sort((first, second) ->
                paymentService.getPaidAmountByCompany(second.getId())
                        .compareTo(paymentService.getPaidAmountByCompany(first.getId())));

        List<CompanyDTO> result = mapperUtil.mapList(companies, CompanyDTO.class);

        for (CompanyDTO dto : result) {
            dto.setRevenue(paymentService.getPaidAmountByCompany(dto.getId()));
        }

        return result;
    }
}
