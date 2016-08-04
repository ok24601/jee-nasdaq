package hrytsenko.nasdaq.endpoint;

import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;
import javax.inject.Inject;

@Startup
@Singleton
public class NasdaqService {

    private static final Logger LOGGER = Logger.getLogger(NasdaqService.class.getName());

    private NasdaqEndpoint nasdaqEndpoint;

    @Inject
    public void setNasdaqService(NasdaqEndpoint nasdaqEndpoint) {
        this.nasdaqEndpoint = nasdaqEndpoint;
    }

    @Schedule(hour = "*", minute = "*", second = "*", persistent = false)
    public void updateCompaniesOnStartup(Timer timer) {
        try {
            updateCompanies();
        } finally {
            timer.cancel();
        }
    }

    @Schedule(hour = "*", minute = "*/10", persistent = false)
    public void updateCompanies() {
        LOGGER.info("Update companies.");

        nasdaqEndpoint.updateCompanies();
    }

}
