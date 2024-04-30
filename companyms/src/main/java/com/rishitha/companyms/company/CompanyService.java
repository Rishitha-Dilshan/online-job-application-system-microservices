package com.rishitha.companyms.company;
import java.util.List;


public interface CompanyService {
    List<Company> getAllcompanies();
    boolean updateCompany(Long id,Company company);

    void createCompany(Company company);

    Company getCompanyById(Long id);

    boolean deleteCompanyById(Long id);



}
