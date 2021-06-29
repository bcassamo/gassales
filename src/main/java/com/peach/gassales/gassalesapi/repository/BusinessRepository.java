package com.peach.gassales.gassalesapi.repository;

import com.peach.gassales.gassalesapi.model.Lancamento;
import com.peach.gassales.gassalesapi.repository.business.BusinessRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Lancamento, Long>, BusinessRepositoryQuery {
}
