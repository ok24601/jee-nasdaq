package hrytsenko.nasdaq.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "exchange")
    private String exchange;
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "name")
    private String name;

    @Column(name = "sector")
    private String sector;
    @Column(name = "subsector")
    private String subsector;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Version
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public Company updateFrom(Company other) {
        setName(other.getName());
        setSector(other.getSector());
        setSubsector(other.getSubsector());

        return this;
    }

}
