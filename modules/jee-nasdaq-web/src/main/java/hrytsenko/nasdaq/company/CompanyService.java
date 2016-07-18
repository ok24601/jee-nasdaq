package hrytsenko.nasdaq.company;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.google.common.base.Preconditions;

import hrytsenko.nasdaq.domain.Company;

@Stateless
public class CompanyService {

    private CompanyRepository companyRepository;

    @Inject
    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findCompanies(Optional<String> exchange, Optional<String> sector, Optional<String> symbol) {
        return companyRepository.findCompanies(exchange, sector, symbol);
    }

    public void updateCompany(Company company) {
        Preconditions.checkNotNull(company);

        companyRepository.updateCompany(company);
    }

}
