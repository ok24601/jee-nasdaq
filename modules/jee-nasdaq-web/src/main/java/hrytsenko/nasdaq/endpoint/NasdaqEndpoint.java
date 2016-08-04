package hrytsenko.nasdaq.endpoint;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import hrytsenko.nasdaq.AppException;
import hrytsenko.nasdaq.common.Auditable;
import hrytsenko.nasdaq.common.Property;
import hrytsenko.nasdaq.company.CompanyService;
import hrytsenko.nasdaq.domain.Company;

@Stateless
public class NasdaqEndpoint {

    private static final Logger LOGGER = Logger.getLogger(NasdaqEndpoint.class.getName());

    private String linkToDownload;
    private String[] exchanges;

    private CompanyService companyService;

    @Inject
    public void setLinkToDownload(@Property("link_to_download_by_exchange") String linkToDownload) {
        this.linkToDownload = linkToDownload;
    }

    @Inject
    public void setExchanges(@Property("exchanges") String[] exchanges) {
        this.exchanges = exchanges;
    }

    @Inject
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Auditable
    @Asynchronous
    public void updateCompanies() {
        Stream.of(exchanges).forEach(exchange -> loadCompanies(exchange).forEach(companyService::updateCompany));
    }

    private List<Company> loadCompanies(String exchange) {
        LOGGER.info(() -> String.format("Load companies for %s.", exchange));
        String content = loadData(linkToDownload.replace("{exchange}", exchange));
        return NasdaqParser.parseCompanies(exchange, content);
    }

    protected String loadData(String link) {
        try {
            return IOUtils.toString(new URL(link), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new AppException(String.format("Cannot load data from %s.", link), exception);
        }
    }

}
