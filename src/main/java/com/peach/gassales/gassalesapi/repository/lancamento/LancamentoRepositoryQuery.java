package com.peach.gassales.gassalesapi.repository.lancamento;

import com.peach.gassales.gassalesapi.model.Lancamento;
import com.peach.gassales.gassalesapi.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
