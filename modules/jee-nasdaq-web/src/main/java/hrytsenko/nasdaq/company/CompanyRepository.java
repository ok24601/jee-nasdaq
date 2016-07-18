package hrytsenko.nasdaq.company;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import hrytsenko.nasdaq.domain.Company;
import hrytsenko.nasdaq.domain.Company_;

@Stateless
public class CompanyRepository {

    @PersistenceContext(unitName = "nasdaq-unit")
    private EntityManager entityManager;

    public List<Company> findCompanies(Optional<String> exchange, Optional<String> sector, Optional<String> symbol) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> query = builder.createQuery(Company.class);
        Root<Company> root = query.from(Company.class);

        query.select(root);
        query.where(builder.and(
                exchange.map(value -> builder.equal(root.get(Company_.exchange), value)).orElse(builder.conjunction()),
                sector.map(value -> builder.equal(root.get(Company_.sector), value)).orElse(builder.conjunction())),
                symbol.map(value -> builder.equal(root.get(Company_.symbol), value)).orElse(builder.conjunction()));

        return entityManager.createQuery(query).getResultList();
    }

    public void updateCompany(Company company) {
        Company updatedCompany = findCompanies(Optional.of(company.getExchange()), Optional.empty(),
                Optional.of(company.getSymbol())).stream().findFirst().orElse(company).updateFrom(company);

        entityManager.merge(updatedCompany);
    }

}
