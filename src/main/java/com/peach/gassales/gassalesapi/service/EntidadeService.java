package com.peach.gassales.gassalesapi.service;

import com.peach.gassales.gassalesapi.model.Entidade;
import com.peach.gassales.gassalesapi.model.TipoEntidade;
import com.peach.gassales.gassalesapi.repository.EntidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EntidadeService {
    private EntidadeRepository entidadeRepository;

    List<Entidade> clientes;
    List<Entidade> fornecedores;

    @Autowired
    private void setClienteRepository(EntidadeRepository entidadeRepository) {
        this.entidadeRepository = entidadeRepository;
    }

    /**
     * Procura Cliente atraves do seu codigo
     * @param id parametro
     * @return Cliente Optional
     * @throws EmptyResultDataAccessException se não encontrar cliente com o id fornecido
     */
    public Optional<Entidade> buscarClientePeloCodigo(Long id) {
        Optional<Entidade> cliente = entidadeRepository.findById(id);
        if (cliente.isEmpty())
                throw new EmptyResultDataAccessException(1);
        return cliente;
    }

    public Entidade actualizar(Long id, Entidade entidade) {
        Entidade entidadeSalvo = entidadeRepository.findById(id).get();
        BeanUtils.copyProperties(entidade, entidadeSalvo, "id");
        return entidadeRepository.save(entidadeSalvo);
    }

    public List<Entidade> buscarTodosClientes() {
        return seleccionarEntidades(TipoEntidade.CLIENTE);
    }

    public List<Entidade> buscarTodosFornecedores() {
        return seleccionarEntidades(TipoEntidade.FORNECEDOR);
    }

    private List<Entidade> seleccionarEntidades(TipoEntidade tipoEntidade) {
        List<Entidade> entidadesSalvas = entidadeRepository.findAll();
        List<Entidade> entidades = new ArrayList<>();
        for (Entidade entidade: entidadesSalvas) {
            if (tipoEntidade.equals(entidade.getTipo())) {
                entidades.add(entidade);
            }
        }
        return entidades;
    }
}
