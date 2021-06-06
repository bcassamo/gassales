package com.peach.gassales.gassalesapi.resource;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.Entidade;
import com.peach.gassales.gassalesapi.repository.EntidadeRepository;
import com.peach.gassales.gassalesapi.service.EntidadeService;
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
@RequestMapping("/entidades")
public class EntidadeResource {
    private EntidadeRepository entidadeRepository;
    private EntidadeService entidadeService;
    private ApplicationEventPublisher publisher;

    @Autowired
    private void setClienteRepository(EntidadeRepository entidadeRepository) {
        this.entidadeRepository = entidadeRepository;
    }

    @Autowired
    private void setClienteService(EntidadeService entidadeService) {
        this.entidadeService = entidadeService;
    }

    @Autowired
    private void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping
    public List<Entidade> listar() { return entidadeRepository.findAll(); }

    @GetMapping("/clientes")
    public List<Entidade> listarClientes() {
        return entidadeService.buscarTodosClientes();
    }

    @GetMapping("/fornecedores")
    public List<Entidade> listarFornecedores(){
        return entidadeService.buscarTodosFornecedores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entidade> buscarClientePeloCodigo(@PathVariable Long id) {
        Optional<Entidade> cliente = entidadeService.buscarClientePeloCodigo(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        entidadeRepository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<Entidade> criar(@Valid @RequestBody Entidade entidade, HttpServletResponse response) {
        Entidade entidadeSalvo = entidadeRepository.save(entidade);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, entidadeSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(entidadeSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entidade> actualizar(@PathVariable Long id, @Valid @RequestBody Entidade entidade) {
        Entidade entidadeSalvo = entidadeService.actualizar(id, entidade);
        return ResponseEntity.ok(entidadeSalvo);
    }
}
