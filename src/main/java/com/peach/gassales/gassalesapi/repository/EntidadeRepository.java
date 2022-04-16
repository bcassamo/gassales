package com.peach.gassales.gassalesapi.repository;

import com.peach.gassales.gassalesapi.model.Entidade;
import com.peach.gassales.gassalesapi.model.TipoEntidade;
import com.peach.gassales.gassalesapi.repository.Entidade.EntidadeRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntidadeRepository extends JpaRepository<Entidade, Long>, EntidadeRepositoryQuery {
    List<Entidade> findAllByTipo(TipoEntidade tipoEntidade);
    Optional<Entidade> findEntidadeByTipoAndId(TipoEntidade tipoEntidade, Long id);
    void deleteByTipoAndId(TipoEntidade tipoEntidade, Long id);

    Entidade findByNome(String nome);
}
