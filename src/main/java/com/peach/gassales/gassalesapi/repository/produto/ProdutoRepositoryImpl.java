package com.peach.gassales.gassalesapi.repository.produto;

import com.peach.gassales.gassalesapi.model.Produto;
import com.peach.gassales.gassalesapi.model.Produto_;
import com.peach.gassales.gassalesapi.repository.filter.ProdutoFilter;
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

public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Produto> filtrar(ProdutoFilter produtoFilter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        Predicate[] predicates = criarRestricoes(produtoFilter, criteriaBuilder, root);
        criteriaQuery.where(predicates);

        TypedQuery<Produto> query = manager.createQuery(criteriaQuery);
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(produtoFilter));
    }

    private Predicate[] criarRestricoes(ProdutoFilter produtoFilter, CriteriaBuilder criteriaBuilder, Root<Produto> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(produtoFilter.getNome())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Produto_.nome)),
                    "%" + produtoFilter.getNome().toLowerCase() + "%"));
        }

        if(StringUtils.hasText(produtoFilter.getReferencia())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Produto_.referencia)),
                    "%" + produtoFilter.getReferencia().toLowerCase() + "%"));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Produto> query, Pageable pageable) {
        int paginaActual = pageable.getPageNumber();
        int totalRegistosPorPagina = pageable.getPageSize();
        int primeiroregistoDaPagina = paginaActual * totalRegistosPorPagina;

        query.setFirstResult(primeiroregistoDaPagina);
        query.setMaxResults(totalRegistosPorPagina);
    }

    private Long total(ProdutoFilter produtoFilter) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        Predicate[] predicates = criarRestricoes(produtoFilter, criteriaBuilder, root);
        criteriaQuery.where(predicates);

        criteriaQuery.select(criteriaBuilder.count(root));
        return manager.createQuery(criteriaQuery).getSingleResult();
    }
}
