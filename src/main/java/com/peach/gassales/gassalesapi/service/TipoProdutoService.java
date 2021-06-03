package com.peach.gassales.gassalesapi.service;

import com.peach.gassales.gassalesapi.model.TipoProduto;
import com.peach.gassales.gassalesapi.repository.TipoProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TipoProdutoService {
    private TipoProdutoRepository tipoProdutoRepository;

    @Autowired
    private void setTipoProdutoRepository(TipoProdutoRepository tipoProdutoRepository) { this.tipoProdutoRepository = tipoProdutoRepository; }

    public TipoProduto actualizar(Long id, TipoProduto tipoProduto) {
        TipoProduto tipoProdutoSalvo = tipoProdutoRepository.findById(id).get();
        BeanUtils.copyProperties(tipoProduto, tipoProdutoSalvo, "id");
        return tipoProdutoRepository.save(tipoProdutoSalvo);
    }

    /**
     * Procura Tipo de Produto pelo id
     * @param id parametro
     * @return Tipo de Produto Optional
     * @throws EmptyResultDataAccessException Se o resultado for vazio
     */
    public Optional<TipoProduto> buscarTipoProdutoPeloCodigo(Long id) {
        Optional<TipoProduto> tipoProduto = tipoProdutoRepository.findById(id);
        if(tipoProduto.isEmpty())
            throw new EmptyResultDataAccessException(1);
        return tipoProduto;
    }
}
