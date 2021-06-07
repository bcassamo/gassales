package com.peach.gassales.gassalesapi.service;

import com.peach.gassales.gassalesapi.model.Endereco;
import com.peach.gassales.gassalesapi.model.Entidade;
import com.peach.gassales.gassalesapi.model.TipoEntidade;
import com.peach.gassales.gassalesapi.repository.EntidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EntidadeService {
    private EntidadeRepository entidadeRepository;

    @Autowired
    private void setEntidadeRepository(EntidadeRepository entidadeRepository) {
        this.entidadeRepository = entidadeRepository;
    }

    /**
     * Procura Cliente atraves do seu codigo
     * @param id parametro
     * @return Cliente Optional
     * @throws EmptyResultDataAccessException se não encontrar cliente com o id fornecido
     */
    public Optional<Entidade> buscarClientePeloCodigo(Long id) {
        Optional<Entidade> cliente = entidadeRepository.findEntidadeByTipoAndId(TipoEntidade.CLIENTE, id);
        if (cliente.isEmpty())
                throw new EmptyResultDataAccessException(1);
        return cliente;
    }

    public Optional<Entidade> buscarFornecedorPeloCodigo(Long id) {
        Optional<Entidade> fornecedor = entidadeRepository.findEntidadeByTipoAndId(TipoEntidade.FORNECEDOR, id);
        if (fornecedor.isEmpty())
            throw new EmptyResultDataAccessException(1);
        return fornecedor;
    }

    public Entidade actualizarCliente(Long id, Entidade entidade) {
        Entidade clienteSalvo = entidadeRepository.findEntidadeByTipoAndId(TipoEntidade.CLIENTE, id).get();
        if(entidade.getTipo().equals(TipoEntidade.FORNECEDOR))
            throw new DataIntegrityViolationException("Esta tentar actualizar um cliente");

        BeanUtils.copyProperties(entidade, clienteSalvo, "id");
        return entidadeRepository.save(clienteSalvo);
    }

    public Entidade actualizarFornecedor(Long id, Entidade entidade) {
        Entidade forncedorSalvo = entidadeRepository.findEntidadeByTipoAndId(TipoEntidade.FORNECEDOR, id).get();
        if(entidade.getTipo().equals(TipoEntidade.CLIENTE))
            throw new DataIntegrityViolationException("Esta tentar actualizar um fornecedor");

        BeanUtils.copyProperties(entidade, forncedorSalvo, "id");
        return entidadeRepository.save(forncedorSalvo);
    }

    public Entidade actualizarEnderecoDoCliente(Long id, Endereco endereco) {
        Entidade clienteSalvo = entidadeRepository.findEntidadeByTipoAndId(TipoEntidade.CLIENTE, id).get();
        if(clienteSalvo.getTipo().equals(TipoEntidade.FORNECEDOR))
            throw new DataIntegrityViolationException("Esta tentar actualizar endereco de um cliente");

        clienteSalvo.setEndereco(endereco);
        return entidadeRepository.save(clienteSalvo);
    }

    public Entidade actualizarEnderecoDoFornecedor(Long id, Endereco endereco) {
        Entidade fornecedorSalvo = entidadeRepository.findEntidadeByTipoAndId(TipoEntidade.FORNECEDOR, id).get();
        if(fornecedorSalvo.getTipo().equals(TipoEntidade.CLIENTE))
            throw new DataIntegrityViolationException("Esta tentar actualizar endereco de um fornecedor");

        fornecedorSalvo.setEndereco(endereco);
        return entidadeRepository.save(fornecedorSalvo);
    }

    public List<Entidade> buscarTodosClientes() {
        return seleccionarEntidades(TipoEntidade.CLIENTE);
    }

    public List<Entidade> buscarTodosFornecedores() {
        return seleccionarEntidades(TipoEntidade.FORNECEDOR);
    }

    @Transactional
    public Optional<Entidade> removerCliente(Long id) {
        Optional<Entidade> cliente = entidadeRepository.findEntidadeByTipoAndId(TipoEntidade.CLIENTE, id);
        if (cliente.isEmpty())
            throw new EmptyResultDataAccessException(1);
        entidadeRepository.deleteByTipoAndId(TipoEntidade.CLIENTE, id);
        return cliente;
    }

    @Transactional
    public Optional<Entidade> removerFornecedor(Long id) {
        Optional<Entidade> fornecedor = entidadeRepository.findEntidadeByTipoAndId(TipoEntidade.FORNECEDOR, id);
        if (fornecedor.isEmpty())
            throw new EmptyResultDataAccessException(1);
        entidadeRepository.deleteByTipoAndId(TipoEntidade.FORNECEDOR, id);
        return fornecedor;
    }

    private List<Entidade> seleccionarEntidades(TipoEntidade tipoEntidade) {
        return entidadeRepository.findAllByTipo(tipoEntidade);
    }
}
