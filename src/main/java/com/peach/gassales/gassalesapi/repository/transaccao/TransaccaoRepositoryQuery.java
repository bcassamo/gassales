package com.peach.gassales.gassalesapi.repository.transaccao;

import com.peach.gassales.gassalesapi.model.Transaccao;
import com.peach.gassales.gassalesapi.repository.filter.TransaccaoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransaccaoRepositoryQuery {
    Page<Transaccao> filtrar(TransaccaoFilter transaccaoFilter, Pageable pageable);
}
