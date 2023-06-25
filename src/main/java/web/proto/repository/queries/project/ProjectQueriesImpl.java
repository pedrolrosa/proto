package web.proto.repository.queries.project;

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
import web.proto.model.Project;
import web.proto.model.enums.Area;
import web.proto.model.enums.State;
import web.proto.model.filter.ProjectFilter;
import web.proto.repository.pagination.PaginacaoUtil;

public class ProjectQueriesImpl implements ProjectQueries {

    @PersistenceContext
    private EntityManager manager;
    private static final String DATA_CREATED = "dataCreated";

    @Override
    public Page<Project> searchWithFilter(ProjectFilter filter, Pageable pageable) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = builder.createQuery(Project.class);
        Root<Project> p = criteriaQuery.from(Project.class);
        TypedQuery<Project> typedQuery;
        List<Predicate> predicateList = new ArrayList<>();

        if (StringUtils.hasText(filter.getName())) {
            predicateList.add(builder.like(builder.lower(p.<String>get("name")),
                    "%" + filter.getName().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(filter.getDescription())) {
            predicateList.add(builder.like(builder.lower(p.<String>get("description")),
                    "%" + filter.getDescription().toLowerCase() + "%"));
        }

        if (filter.getArea() != null) {
            predicateList.add(builder.equal(p.<Area>get("area"), filter.getArea()));
        }

        if (filter.getState() != null) {
            predicateList.add(builder.equal(p.<State>get("state"), filter.getState()));
        }

        if (filter.getDateCreated() != null) {
            predicateList.add(builder.greaterThanOrEqualTo(
                    p.<LocalDate>get(DATA_CREATED), filter.getDateCreated()));
        }

        if (filter.getDateCreated() != null) {
            predicateList.add(builder.lessThanOrEqualTo(
                    p.<LocalDate>get(DATA_CREATED), filter.getDateCreated()));
        }

        predicateList.add(builder.equal(p.<Boolean>get("active"), true));

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(p).where(predArray);
        PaginacaoUtil.prepararOrdem(p, criteriaQuery, builder, pageable);

        typedQuery = manager.createQuery(criteriaQuery);
        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);

        List<Project> projects = typedQuery.getResultList();

        long totalProjects = getTotalProjects(filter);

        Page<Project> page = new PageImpl<>(projects, pageable, totalProjects);
        return page;

    }

    private Long getTotalProjects(ProjectFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Project> p = criteriaQuery.from(Project.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (StringUtils.hasText(filter.getName())) {
            predicateList.add(builder.like(builder.lower(p.<String>get("name")),
                    "%" + filter.getName().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(filter.getDescription())) {
            predicateList.add(builder.like(builder.lower(p.<String>get("description")),
                    "%" + filter.getDescription().toLowerCase() + "%"));
        }

        if (filter.getArea() != null) {
            predicateList.add(builder.equal(p.<Area>get("area"), filter.getArea()));
        }

        if (filter.getState() != null) {
            predicateList.add(builder.equal(p.<State>get("state"), filter.getState()));
        }

        if (filter.getDateCreated() != null) {
            predicateList.add(builder.greaterThanOrEqualTo(
                    p.<LocalDate>get(DATA_CREATED), filter.getDateCreated()));
        }

        if (filter.getDateCreated() != null) {
            predicateList.add(builder.lessThanOrEqualTo(
                    p.<LocalDate>get(DATA_CREATED), filter.getDateCreated()));
        }

        predicateList.add(builder.equal(p.<Boolean>get("active"), true));

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(builder.count(p)).where(predArray);

        return manager.createQuery(criteriaQuery).getSingleResult();

    }

}
