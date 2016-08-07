package hrytsenko.nasdaq.endpoint;

import javax.ejb.Timer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class NasdaqServiceTest {

    private NasdaqService nasdaqService;
    private NasdaqEndpoint nasdaqEndpoint;

    @Before
    public void init() {
        nasdaqEndpoint = Mockito.mock(NasdaqEndpoint.class);

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

        Mockito.verify(nasdaqEndpoint).updateCompanies();
    }

}
