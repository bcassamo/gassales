package com.peach.gassales.gassalesapi.repository;

import com.peach.gassales.gassalesapi.model.Produto;
import com.peach.gassales.gassalesapi.repository.produto.ProdutoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery {
}
