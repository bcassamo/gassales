package com.peach.gassales.gassalesapi.service;

import com.peach.gassales.gassalesapi.model.Produto;
import com.peach.gassales.gassalesapi.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {
    private ProdutoRepository produtoRepository;

    @Autowired
    private void setProdutoRepository(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /**
     * Procura Produto pelo codigo
     * @param id parametro
     * @return Produto Optional
     * @throws EmptyResultDataAccessException se o resultado for vazio
     */
    public Optional<Produto> buscarProdutoPeloCodigo(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isEmpty())
            throw new EmptyResultDataAccessException(1);
        return produto;
    }

    public Produto actualizar(Long id, Produto produto) {
        Produto produtoSalvo = produtoRepository.findById(id).get();
        BeanUtils.copyProperties(produto, produtoSalvo, "id");
        return produtoRepository.save(produtoSalvo);
    }
}
