package com.rishitha.companyms.company.impl;

import com.rishitha.companyms.company.Company;
import com.rishitha.companyms.company.CompanyRepository;
import com.rishitha.companyms.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class companyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    public companyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllcompanies() {
        return companyRepository.findAll();
    }



    @Override
    public boolean updateCompany(Long id, Company updatedcompany) {
        Optional<Company> companyoptional = companyRepository.findById(id);

        if(companyoptional.isPresent()){
            Company company = companyoptional.get();
            company.setDescription(updatedcompany.getDescription());
            company.setName(updatedcompany.getName());
            companyRepository.save(company);
            return true;
        }

        return false;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        if(companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

}
