package com.peach.gassales.gassalesapi.repository.produto;

import com.peach.gassales.gassalesapi.model.Produto;
import com.peach.gassales.gassalesapi.repository.filter.ProdutoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoRepositoryQuery {
    Page<Produto> filtrar(ProdutoFilter produtoFilter, Pageable pageable);
}
