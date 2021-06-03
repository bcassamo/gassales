package com.peach.gassales.gassalesapi.repository;

import com.peach.gassales.gassalesapi.model.Lancamento;
import com.peach.gassales.gassalesapi.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {
}
