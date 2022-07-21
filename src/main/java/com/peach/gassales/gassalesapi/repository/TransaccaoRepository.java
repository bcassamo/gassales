package com.peach.gassales.gassalesapi.repository;

import com.peach.gassales.gassalesapi.model.Transaccao;
import com.peach.gassales.gassalesapi.repository.transaccao.TransaccaoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaccaoRepository extends JpaRepository<Transaccao, Long>, TransaccaoRepositoryQuery {
    Transaccao findTopByOrderByIdDesc();
    List<Transaccao> findAllByIdTransaccao(String idTransaccao);
}
