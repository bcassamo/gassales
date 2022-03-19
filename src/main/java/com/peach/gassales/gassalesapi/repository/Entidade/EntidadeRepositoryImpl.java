package com.peach.gassales.gassalesapi.repository.Entidade;

import com.peach.gassales.gassalesapi.model.Entidade;
import com.peach.gassales.gassalesapi.model.Entidade_;
import com.peach.gassales.gassalesapi.model.TipoEntidade;
import com.peach.gassales.gassalesapi.repository.filter.EntidadeFilter;
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

public class EntidadeRepositoryImpl implements EntidadeRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Entidade> filtrar(EntidadeFilter entidadeFilter, Pageable pageable, TipoEntidade tipo) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Entidade> criteriaQuery = criteriaBuilder.createQuery(Entidade.class);
        Root<Entidade> root = criteriaQuery.from(Entidade.class);

        Predicate[] predicates = criarRestricoes(entidadeFilter, criteriaBuilder, root, tipo);
        criteriaQuery.where(predicates);

        TypedQuery<Entidade> query = manager.createQuery(criteriaQuery);
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(entidadeFilter, tipo));
    }

    private Predicate[] criarRestricoes(EntidadeFilter entidadeFilter, CriteriaBuilder criteriaBuilder, Root<Entidade> root, TipoEntidade tipo) {
        List<Predicate> predicates = new ArrayList<>();

        if(entidadeFilter.getNome() == null && entidadeFilter.getNuit() == null) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(Entidade_.tipo.getName())),
                    tipo));
        }

        if (StringUtils.hasText(entidadeFilter.getNome())) {
            Predicate entidade_nome = criteriaBuilder.like(criteriaBuilder.lower(root.get(Entidade_.nome)),
                    "%" + entidadeFilter.getNome().toLowerCase() + "%");

            Predicate entidade_tipo =criteriaBuilder.equal(criteriaBuilder.lower(root.get(Entidade_.tipo.getName())),
                    tipo);
            Predicate nomeETipo = criteriaBuilder.and(entidade_nome, entidade_tipo);
            predicates.add(nomeETipo);
        }

        if (entidadeFilter.getNuit() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Entidade_.nuit)),
                    "%" + entidadeFilter.getNuit() + "%"));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Entidade> query, Pageable pageable) {
        int paginaActual = pageable.getPageNumber();
        int totalRegistosPorPagina = pageable.getPageSize();
        int primeiroRegistoDaPagina = paginaActual * totalRegistosPorPagina;

        query.setFirstResult(primeiroRegistoDaPagina);
        query.setMaxResults(totalRegistosPorPagina);
    }

    private Long total(EntidadeFilter entidadeFilter, TipoEntidade tipo) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Entidade> root = criteriaQuery.from(Entidade.class);

        Predicate[] predicates = criarRestricoes(entidadeFilter, criteriaBuilder, root, tipo);
        criteriaQuery.where(predicates);

        criteriaQuery.select(criteriaBuilder.count(root));
        return manager.createQuery(criteriaQuery).getSingleResult();
    }
}
