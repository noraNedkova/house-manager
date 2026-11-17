package com.housemanager.service;

import com.housemanager.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {

    List<CompanyDTO> getAllCompanies();

    CompanyDTO getCompanyById(long id);

    CompanyDTO createCompany(CompanyDTO company);

    CompanyDTO updateCompany(CompanyDTO company, long id);

    void deleteCompany(long id);

    List<CompanyDTO> getCompaniesSortedByRevenue();
}