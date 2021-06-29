package com.peach.gassales.gassalesapi.repository.business;

import com.peach.gassales.gassalesapi.model.Business;
import com.peach.gassales.gassalesapi.model.Business_;
import com.peach.gassales.gassalesapi.repository.filter.BusinessFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class BusinessRepositoryImpl implements BusinessRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Page<Business> filtrar(BusinessFilter businessFilter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Business> criteriaQuery = criteriaBuilder.createQuery(Business.class);
        Root<Business> root = criteriaQuery.from(Business.class);

        Predicate[] predicates = criarRestricoes(businessFilter, criteriaBuilder, root);
        criteriaQuery.where(predicates);

        TypedQuery<Business> query = manager.createQuery(criteriaQuery);
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(businessFilter));
    }

    //TODO: filtrarVenda()

    private Predicate[] criarRestricoes(BusinessFilter businessFilter, CriteriaBuilder criteriaBuilder, Root<Business> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(businessFilter.getDescricao())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Business_.descricao)),
                    "%" + businessFilter.getDescricao().toLowerCase() + "%"));
        }

        if(businessFilter.getDataBusinessDe() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Business_.dataBusiness), businessFilter.getDataBusinessDe()));
        }

        if(businessFilter.getDataBusinessAte() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Business_.dataBusiness), businessFilter.getDataBusinessAte()));
        }

        // if(businessFilter.getEntidade() != null) {
            // predicates.add(criteriaBuilder.like(root.get(Business_.entidade.getName()),
            //        "%" + businessFilter.getEntidade() + "%"));
        // }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Business> query, Pageable pageable) {
        int paginaActual = pageable.getPageNumber();
        int totalRegistosPorPagina = pageable.getPageSize();
        int primeiroRegistoDaPagina = paginaActual * totalRegistosPorPagina;

        query.setFirstResult(primeiroRegistoDaPagina);
        query.setMaxResults(totalRegistosPorPagina);
    }

    private Long total(BusinessFilter businessFilter) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Business> root = criteriaQuery.from(Business.class);

        Predicate[] predicates = criarRestricoes(businessFilter, criteriaBuilder, root);
        criteriaQuery.where(predicates);

        criteriaQuery.select(criteriaBuilder.count(root));
        return manager.createQuery(criteriaQuery).getSingleResult();
    }
}
