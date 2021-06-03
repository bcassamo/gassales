package com.peach.gassales.gassalesapi.service;

import com.peach.gassales.gassalesapi.model.Cliente;
import com.peach.gassales.gassalesapi.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;

    @Autowired
    private void setClienteRepository(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Procura Cliente atraves do seu codigo
     * @param id parametro
     * @return Cliente Optional
     * @throws EmptyResultDataAccessException se não encontrar cliente com o id fornecido
     */
    public Optional<Cliente> buscarClientePeloCodigo(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty())
                throw new EmptyResultDataAccessException(1);
        return cliente;
    }

    public Cliente actualizar(Long id, Cliente cliente) {
        Cliente clienteSalvo = clienteRepository.findById(id).get();
        BeanUtils.copyProperties(cliente, clienteSalvo, "id");
        return clienteRepository.save(clienteSalvo);
    }
}
