package com.peach.gassales.gassalesapi.resource;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.Endereco;
import com.peach.gassales.gassalesapi.model.Entidade;
import com.peach.gassales.gassalesapi.model.TipoEntidade;
import com.peach.gassales.gassalesapi.repository.EntidadeRepository;
import com.peach.gassales.gassalesapi.repository.filter.EntidadeFilter;
import com.peach.gassales.gassalesapi.service.EntidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Entidade> pesquisar(EntidadeFilter entidadeFilter, Pageable pageable) {
        return entidadeRepository.filtrar(entidadeFilter, pageable);
    }

    @PostMapping
    public ResponseEntity<Entidade> criarEntidade(@Valid @RequestBody Entidade entidade, HttpServletResponse response) {
        Entidade entidadeSalvo = entidadeRepository.save(entidade);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, entidadeSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(entidadeSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entidade> buscarPeloCodigo(@PathVariable Long id){
        Optional<Entidade> entidade = entidadeService.buscarPeloCodigo(id);
        return entidade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/clientes")
    public Page<Entidade> pesquisarClientes(EntidadeFilter entidadeFilter, Pageable pageable) {
        return entidadeRepository.filtrarByTipo(entidadeFilter, pageable, TipoEntidade.CLIENTE);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Entidade> buscarClientePeloCodigo(@PathVariable Long id) {
        Optional<Entidade> cliente = entidadeService.buscarClientePeloCodigo(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerCliente(@PathVariable Long id) {
        entidadeService.removerCliente(id);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Entidade> actualizarCliente(@PathVariable Long id, @Valid @RequestBody Entidade entidade) {
        Entidade clienteSalvo = entidadeService.actualizarCliente(id, entidade);
        return ResponseEntity.ok(clienteSalvo);
    }

    @PutMapping("/clientes/{id}/endereco")
    public ResponseEntity<Entidade> actualizarEnderecoDoCliente(@PathVariable Long id, @Valid @RequestBody Endereco endereco) {
        Entidade clienteSalvo = entidadeService.actualizarEnderecoDoCliente(id, endereco);
        return ResponseEntity.ok(clienteSalvo);
    }

    @GetMapping("/fornecedores")
    public List<Entidade> listarFornecedores(){
        return entidadeService.buscarTodosFornecedores();
    }

    @GetMapping("/fornecedores/{id}")
    public ResponseEntity<Entidade> buscarFornecedorPeloCodigo(@PathVariable Long id) {
        Optional<Entidade> fornecedor = entidadeService.buscarFornecedorPeloCodigo(id);
        return fornecedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/fornecedores/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFornecedor(@PathVariable Long id) {
        entidadeService.removerFornecedor(id);
    }

    @PutMapping("/fornecedores/{id}")
    public ResponseEntity<Entidade> actualizarFornecedor(@PathVariable Long id, @Valid @RequestBody Entidade entidade) {
        Entidade fornecedorSalvo = entidadeService.actualizarFornecedor(id, entidade);
        return ResponseEntity.ok(fornecedorSalvo);
    }

    @PutMapping("/fornecedores/{id}/endereco")
    public ResponseEntity<Entidade> actualizarEnderecoDoFornecedor(@PathVariable Long id, @Valid @RequestBody Endereco endereco) {
        Entidade clienteSalvo = entidadeService.actualizarEnderecoDoFornecedor(id, endereco);
        return ResponseEntity.ok(clienteSalvo);
    }
}
