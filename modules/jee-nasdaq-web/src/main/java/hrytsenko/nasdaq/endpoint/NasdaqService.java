package hrytsenko.nasdaq.endpoint;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import hrytsenko.nasdaq.AppException;

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

    @Produces
    public NasdaqEndpoint.Connector createConnector() {
        return (String link) -> {
            try {
                return IOUtils.toString(new URL(link), StandardCharsets.UTF_8);
            } catch (IOException exception) {
                throw new AppException(String.format("Cannot load data from %s.", link), exception);
            }
        };
    }

}
