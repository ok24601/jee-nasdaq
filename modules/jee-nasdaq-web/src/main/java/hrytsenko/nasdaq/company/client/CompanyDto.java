package hrytsenko.nasdaq.company.client;

import hrytsenko.nasdaq.domain.Company;

public class CompanyDto {

    private String exchange;
    private String symbol;
    private String name;

    private String sector;
    private String subsector;

    public static CompanyDto fromCompany(Company company) {
        CompanyDto dto = new CompanyDto();
        dto.exchange = company.getExchange();
        dto.symbol = company.getSymbol();
        dto.name = company.getName();
        dto.sector = company.getSector();
        dto.subsector = company.getSubsector();
        return dto;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
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

    public String getSubsector() {
        return subsector;
    }

    public void setSubsector(String subsector) {
        this.subsector = subsector;
    }

}
