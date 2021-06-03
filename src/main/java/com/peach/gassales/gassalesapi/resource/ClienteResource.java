package com.peach.gassales.gassalesapi.resource;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.Cliente;
import com.peach.gassales.gassalesapi.repository.ClienteRepository;
import com.peach.gassales.gassalesapi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
    private ClienteRepository clienteRepository;
    private ClienteService clienteService;
    private ApplicationEventPublisher publisher;

    @Autowired
    private void setClienteRepository(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Autowired
    private void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Autowired
    private void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping
    public List<Cliente> listar() { return clienteRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePeloCodigo(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarClientePeloCodigo(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
        Cliente clienteSalvo = clienteRepository.save(cliente);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        Cliente clienteSalvo = clienteService.actualizar(id, cliente);
        return ResponseEntity.ok(clienteSalvo);
    }
}
