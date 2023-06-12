package web.proto.repository.queries.associate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import web.proto.model.Associate;
import web.proto.model.enums.Status;
import web.proto.model.filter.AssociateFilter;
import web.proto.repository.pagination.PaginacaoUtil;

public class AssociateQueriesImpl implements AssociateQueries {

    @PersistenceContext
    private EntityManager manager;
    private static final String DATA_CREATED = "dataCreted";

    @Override
    public Page<Associate> filtrar(AssociateFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Associate> criteriaQuery = builder.createQuery(Associate.class);
        Root<Associate> a = criteriaQuery.from(Associate.class);
        TypedQuery<Associate> typedQuery;
        List<Predicate> predicateList = new ArrayList<>();

        if (filtro.getId() != null) {
            predicateList.add(builder.equal(a.<Long>get("id"), filtro.getId()));
        }

        if (StringUtils.hasText(filtro.getName())) {
            predicateList.add(builder.like(
                    builder.lower(a.<String>get("name")),
                    "%" + filtro.getName().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(filtro.getEmail())) {
            predicateList.add(builder.like(
                    builder.lower(a.<String>get("email")),
                    "%" + filtro.getEmail().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(filtro.getLogin())) {
            predicateList.add(builder.like(
                    builder.lower(a.<String>get("cpf")),
                    "%" + filtro.getLogin().toLowerCase() + "%"));
        }

        if (filtro.getRole() != null) {
            if (StringUtils.hasText(filtro.getRole().getLabel())) {
                predicateList.add(builder.like(
                    builder.lower(a.<String>get("role")),
                    "%" + filtro.getRole().getLabel().toLowerCase() + "%"));
            }
        }        

        if (filtro.getRelevancy() != null) {
            predicateList.add(builder.equal(
                a.get("relevancy"),
                filtro.getRelevancy()
            ));
        }

        if (filtro.getDateCreated() != null) {
            LocalDate startDate = filtro.getDateCreated().withDayOfMonth(1); // Primeiro dia do mês
            LocalDate endDate = startDate.plusMonths(1).minusDays(1); // Último dia do mês

            predicateList.add(builder.between(
                builder.function("MONTH", Integer.class, a.get(DATA_CREATED)),
                startDate.getMonthValue(),
                endDate.getMonthValue()
            ));

            predicateList.add(builder.equal(
                builder.function("YEAR", Integer.class, a.get(DATA_CREATED)),
                startDate.getYear()
            ));
        }


        predicateList.add(builder.equal(a.<Status>get("status"), Status.ACTIVE));

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(a).where(predArray);

        PaginacaoUtil.prepararOrdem(a, criteriaQuery, builder, pageable);

        typedQuery = manager.createQuery(criteriaQuery);

        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);

        List<Associate> associates = typedQuery.getResultList();
        
        long totalAssociates = getTotalAssociates(filtro);
        return new PageImpl<>(associates, pageable, totalAssociates); 
    }

    private Long getTotalAssociates(AssociateFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Associate> a = criteriaQuery.from(Associate.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (filtro.getId() != null) {
            predicateList.add(builder.equal(a.<Long>get("id"), filtro.getId()));
        }

        if (StringUtils.hasText(filtro.getName())) {
            predicateList.add(builder.like(
                    builder.lower(a.<String>get("name")),
                    "%" + filtro.getName().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(filtro.getEmail())) {
            predicateList.add(builder.like(
                    builder.lower(a.<String>get("email")),
                    "%" + filtro.getEmail().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(filtro.getLogin())) {
            predicateList.add(builder.like(
                    builder.lower(a.<String>get("cpf")),
                    "%" + filtro.getLogin().toLowerCase() + "%"));
        }

        if (filtro.getRole() != null) {
            if (StringUtils.hasText(filtro.getRole().getLabel())) {
                predicateList.add(builder.like(
                    builder.lower(a.<String>get("role")),
                    "%" + filtro.getRole().getLabel().toLowerCase() + "%"));
            }
        } 

        if (filtro.getRelevancy() != null) {
            predicateList.add(builder.equal(
                a.get("relevancy"),
                filtro.getRelevancy()
            ));
        }

        if (filtro.getDateCreated() != null) {
            LocalDate startDate = filtro.getDateCreated().withDayOfMonth(1); // Primeiro dia do mês
            LocalDate endDate = startDate.plusMonths(1).minusDays(1); // Último dia do mês

            predicateList.add(builder.between(
                builder.function("MONTH", Integer.class, a.get(DATA_CREATED)),
                startDate.getMonthValue(),
                endDate.getMonthValue()
            ));

            predicateList.add(builder.equal(
                builder.function("YEAR", Integer.class, a.get(DATA_CREATED)),
                startDate.getYear()
            ));
        }

        predicateList.add(builder.equal(a.<Status>get("status"), Status.ACTIVE));

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(builder.count(a)).where(predArray);

        return manager.createQuery(criteriaQuery).getSingleResult();

    }

}
