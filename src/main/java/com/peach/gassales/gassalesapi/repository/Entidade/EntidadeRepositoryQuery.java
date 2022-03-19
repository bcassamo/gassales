package com.peach.gassales.gassalesapi.repository.Entidade;

import com.peach.gassales.gassalesapi.model.Entidade;
import com.peach.gassales.gassalesapi.model.TipoEntidade;
import com.peach.gassales.gassalesapi.repository.filter.EntidadeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntidadeRepositoryQuery {
    Page<Entidade> filtrar(EntidadeFilter entidadeFilter, Pageable pageable, TipoEntidade tipo);
}
