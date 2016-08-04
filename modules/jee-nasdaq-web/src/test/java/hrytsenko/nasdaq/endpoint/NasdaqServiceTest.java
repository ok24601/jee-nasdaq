package hrytsenko.nasdaq.endpoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.ejb.Timer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.io.Resources;

import hrytsenko.nasdaq.company.CompanyRepository;
import hrytsenko.nasdaq.company.CompanyService;
import hrytsenko.nasdaq.domain.Company;

public class NasdaqServiceTest {

    private CompanyRepository companyRepository;
    private CompanyService companyService;

    private NasdaqEndpoint nasdaqEndpoint;
    private NasdaqService nasdaqService;

    @Before
    public void init() {
        companyRepository = Mockito.mock(CompanyRepository.class);

        companyService = new CompanyService();
        companyService.setCompanyRepository(companyRepository);

        nasdaqEndpoint = Mockito.spy(new NasdaqEndpoint());
        nasdaqEndpoint.setCompanyService(companyService);
        nasdaqEndpoint.setExchanges(new String[] { "NASDAQ", "NYSE" });
        nasdaqEndpoint.setLinkToDownload("companies/{exchange}.csv");
        Mockito.doAnswer(invocation -> loadDataFromResource(invocation.getArgumentAt(0, String.class)))
                .when(nasdaqEndpoint).loadData(Mockito.anyString());

        nasdaqService = new NasdaqService();
        nasdaqService.setNasdaqService(nasdaqEndpoint);
    }

    @Test
    public void testUpdateCompaniesOnStartup() {
        Timer timer = Mockito.mock(Timer.class);
        nasdaqService.updateCompaniesOnStartup(timer);

        Mockito.verify(nasdaqEndpoint).updateCompanies();
        Mockito.verify(timer).cancel();
    }

    @Test
    public void testUpdateCompanies() {
        nasdaqService.updateCompanies();

        Mockito.verify(companyRepository, Mockito.times(3)).updateCompany(Mockito.any(Company.class));
    }

    private String loadDataFromResource(String resourceName) {
        try {
            return Resources.toString(Resources.getResource(resourceName), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot load the resource.");
        }
    }

}
