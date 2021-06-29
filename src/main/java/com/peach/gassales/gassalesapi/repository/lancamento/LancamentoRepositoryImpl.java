package com.peach.gassales.gassalesapi.repository.lancamento;

import com.peach.gassales.gassalesapi.model.Lancamento;
import com.peach.gassales.gassalesapi.model.Lancamento_;
import com.peach.gassales.gassalesapi.repository.filter.LancamentoFilter;
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

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteriaQuery = criteriaBuilder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
        criteriaQuery.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteriaQuery);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder criteriaBuilder, Root<Lancamento> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(lancamentoFilter.getDescricao())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
        }

        if(lancamentoFilter.getDataLancamentoDe() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataLancamento), lancamentoFilter.getDataLancamentoDe()));
        }

        if(lancamentoFilter.getDataLancamentoAte() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataLancamento), lancamentoFilter.getDataLancamentoAte()));
        }

        if(lancamentoFilter.getEstado() != null) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(Lancamento_.estado.getName())), lancamentoFilter.getEstado()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
        int paginaActual = pageable.getPageNumber();
        int totalRegistosPorPagina = pageable.getPageSize();
        int primeiroRegistoDaPagina = paginaActual * totalRegistosPorPagina;

        query.setFirstResult(primeiroRegistoDaPagina);
        query.setMaxResults(totalRegistosPorPagina);
    }

    private Long total(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
        criteriaQuery.where(predicates);

        criteriaQuery.select(criteriaBuilder.count(root));
        return manager.createQuery(criteriaQuery).getSingleResult();
    }
}
