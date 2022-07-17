package com.peach.gassales.gassalesapi.repository.transaccao;

import com.peach.gassales.gassalesapi.model.Transaccao;
import com.peach.gassales.gassalesapi.model.Transaccao_;
import com.peach.gassales.gassalesapi.repository.filter.TransaccaoFilter;
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

public class TransaccaoRepositoryImpl implements TransaccaoRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Transaccao> filtrar(TransaccaoFilter transaccaoFilter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Transaccao> criteriaQuery = criteriaBuilder.createQuery(Transaccao.class);
        Root<Transaccao> root = criteriaQuery.from(Transaccao.class);

        Predicate[] predicates = criarRestricoes(transaccaoFilter, criteriaBuilder, root);
        criteriaQuery.where(predicates);

        TypedQuery<Transaccao> query = manager.createQuery(criteriaQuery);
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, (Long) total(transaccaoFilter));
    }

    private Predicate[] criarRestricoes(TransaccaoFilter transaccaoFilter, CriteriaBuilder criteriaBuilder, Root<Transaccao> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(transaccaoFilter.getIdTransaccao())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Transaccao_.idTransaccao)),
                    "%" + transaccaoFilter.getIdTransaccao().toLowerCase() + "%"));
        }

        if(StringUtils.hasText(transaccaoFilter.getTipo())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Transaccao_.tipo)), "%" + transaccaoFilter.getTipo().toLowerCase() + "%"));
        }

        if(transaccaoFilter.getDataTransaccaoDe() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Transaccao_.dataTransaccao), transaccaoFilter.getDataTransaccaoDe()));
        }

        if(transaccaoFilter.getDataTransaccaoAte() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Transaccao_.dataTransaccao), transaccaoFilter.getDataTransaccaoAte()));
        }

        if(transaccaoFilter.getEstado() != null) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(Transaccao_.estado.getName())), transaccaoFilter.getEstado()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Transaccao> query, Pageable pageable) {
        int paginaActual = pageable.getPageNumber();
        int totalRegistosPorPagina = pageable.getPageSize();
        int primeiroRegistoDaPagina = paginaActual * totalRegistosPorPagina;

        query.setFirstResult(primeiroRegistoDaPagina);
        query.setMaxResults(totalRegistosPorPagina);
    }

    private Object total(TransaccaoFilter transaccaoFilter) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Transaccao> root = criteriaQuery.from(Transaccao.class);

        Predicate[] predicates = criarRestricoes(transaccaoFilter, criteriaBuilder, root);
        criteriaQuery.where(predicates);

        criteriaQuery.select(criteriaBuilder.count(root));
        return manager.createQuery(criteriaQuery).getSingleResult();
    }
}
