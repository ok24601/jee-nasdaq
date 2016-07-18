package hrytsenko.nasdaq.company;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hrytsenko.nasdaq.company.client.CompanyDto;
import hrytsenko.nasdaq.company.client.CompanyResource;
import hrytsenko.nasdaq.domain.Company;

public class CompanyResourceTest {

    private CompanyRepository companyRepository;
    private CompanyService companyService;

    private CompanyResource companyResource;

    @Before
    public void init() {
        companyRepository = Mockito.mock(CompanyRepository.class);

        companyService = new CompanyService();
        companyService.setCompanyRepository(companyRepository);

        companyResource = new CompanyResource();
        companyResource.setCompanyService(companyService);
    }

    @Test
    public void testFind() {
        Mockito.doReturn(Arrays.asList(createCompany("NASDAQ", "Technology", "MSFT"))).when(companyRepository)
                .findCompanies(Mockito.any(), Mockito.any(), Mockito.any());

        List<CompanyDto> companies = companyResource.findCompanies(null, null, "MSFT");

        Assert.assertEquals(1, companies.size());

        Mockito.verify(companyRepository).findCompanies(Mockito.eq(Optional.empty()), Mockito.eq(Optional.empty()),
                Mockito.eq(Optional.of("MSFT")));
    }

    private Company createCompany(String exchange, String sector, String symbol) {
        Company company = new Company();
        company.setExchange(exchange);
        company.setSector(sector);
        company.setSymbol(symbol);
        return company;
    }

}
