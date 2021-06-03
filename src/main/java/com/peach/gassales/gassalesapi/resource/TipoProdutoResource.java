package com.peach.gassales.gassalesapi.resource;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.TipoProduto;
import com.peach.gassales.gassalesapi.repository.TipoProdutoRepository;
import com.peach.gassales.gassalesapi.service.TipoProdutoService;
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
@RequestMapping("/tipos")
public class TipoProdutoResource {

    private TipoProdutoRepository tipoProdutoRepository;
    private ApplicationEventPublisher publisher;
    private TipoProdutoService tipoProdutoService;

    @Autowired
    private void setTipoProdutoRepository(TipoProdutoRepository tipoProdutoRepository) {
        this.tipoProdutoRepository = tipoProdutoRepository;
    }

    @Autowired
    private void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Autowired
    private void setTipoProdutoService(TipoProdutoService tipoProdutoService) { this.tipoProdutoService = tipoProdutoService; }

    @GetMapping
    public List<TipoProduto> listar() {
        return tipoProdutoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<TipoProduto> criar(@Valid @RequestBody TipoProduto tipoProduto, HttpServletResponse response) {
        TipoProduto tipoProdutoSalvo = tipoProdutoRepository.save(tipoProduto);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, tipoProdutoSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoProdutoSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProduto> buscarPeloCodigo(@PathVariable Long id) {
        Optional<TipoProduto> tipoProdutoSalvo = tipoProdutoService.buscarTipoProdutoPeloCodigo(id);
        return tipoProdutoSalvo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void remover(@PathVariable Long id) { tipoProdutoRepository.deleteById(id); }

    @PutMapping("/{id}")
    public ResponseEntity<TipoProduto> actualizar(@PathVariable Long id, @Valid @RequestBody TipoProduto tipoProduto) {
        TipoProduto tipoProdutoSalvo = tipoProdutoService.actualizar(id, tipoProduto);
        return ResponseEntity.ok(tipoProdutoSalvo);
    }
}
