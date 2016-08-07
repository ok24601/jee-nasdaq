package hrytsenko.nasdaq.endpoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.io.Resources;

import hrytsenko.nasdaq.AppException;
import hrytsenko.nasdaq.company.CompanyService;
import hrytsenko.nasdaq.domain.Company;

public class NasdaqEndpointTest {

    private CompanyService companyService;

    private NasdaqEndpoint nasdaqEndpoint;

    @Before
    public void init() {
        companyService = Mockito.mock(CompanyService.class);

        nasdaqEndpoint = Mockito.spy(new NasdaqEndpoint());
        nasdaqEndpoint.setCompanyService(companyService);
        nasdaqEndpoint.setExchanges(new String[] { "NASDAQ", "NYSE" });
        nasdaqEndpoint.setLinkToDownload("companies/{exchange}.csv");
        Mockito.doAnswer(invocation -> loadDataFromResource(invocation.getArgumentAt(0, String.class)))
                .when(nasdaqEndpoint).loadData(Mockito.anyString());
    }

    @Test(expected = AppException.class)
    public void testLoadDataIfUrlNotValid() {
        NasdaqEndpoint endpoint = new NasdaqEndpoint();

        endpoint.loadData("invalid");
    }

    @Test
    public void testUpdateCompanies() {
        nasdaqEndpoint.updateCompanies();

        Mockito.verify(companyService, Mockito.times(3)).updateCompany(Mockito.any(Company.class));
    }

    private String loadDataFromResource(String resourceName) {
        try {
            return Resources.toString(Resources.getResource(resourceName), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot load the resource.");
        }
    }

}
