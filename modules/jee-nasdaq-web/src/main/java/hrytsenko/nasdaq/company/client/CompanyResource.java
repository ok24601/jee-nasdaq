package hrytsenko.nasdaq.company.client;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Strings;

import hrytsenko.nasdaq.company.CompanyService;

@Path("companies")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyResource {

    private CompanyService companyService;

    @Inject
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GET
    public List<CompanyDto> findCompanies(@QueryParam("exchange") String exchange, @QueryParam("sector") String sector,
            @QueryParam("symbol") String symbol) {
        return companyService.findCompanies(optionalOf(exchange), optionalOf(sector), optionalOf(symbol)).stream()
                .map(CompanyDto::fromCompany).collect(Collectors.toList());
    }

    private Optional<String> optionalOf(String parameter) {
        return Optional.ofNullable(Strings.emptyToNull(parameter));
    }

}
