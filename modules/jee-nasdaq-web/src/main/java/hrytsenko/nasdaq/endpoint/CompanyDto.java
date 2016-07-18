package hrytsenko.nasdaq.endpoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import hrytsenko.nasdaq.domain.Company;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDto {

    @JsonProperty("Symbol")
    private String symbol;
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Sector")
    private String sector;
    @JsonProperty("Industry")
    private String industry;

    public static Company toCompany(CompanyDto companyDto) {
        Company company = new Company();
        company.setSymbol(companyDto.getSymbol());
        company.setName(companyDto.getName());
        company.setSector(companyDto.getSector());
        company.setSubsector(companyDto.getIndustry());
        return company;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

}
