package com.peach.gassales.gassalesapi.resource;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.Produto;
import com.peach.gassales.gassalesapi.repository.ProdutoRepository;
import com.peach.gassales.gassalesapi.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
    private ProdutoRepository produtoRepository;
    private ApplicationEventPublisher publisher;
    private ProdutoService produtoService;

    @Autowired
    private void setProdutoRepository(ProdutoRepository produtoRepository) { this.produtoRepository = produtoRepository; }

    @Autowired
    private void setPublisher(ApplicationEventPublisher publisher) { this.publisher = publisher; }

    @Autowired
    private void setProdutoService(ProdutoService produtoService) { this.produtoService = produtoService; }

    @GetMapping
    public List<Produto> listar() { return produtoRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPeloCodigo(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarProdutoPeloCodigo(id);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto, HttpServletResponse response) {
        Produto produtoSalvo = produtoRepository.save(produto);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) { produtoRepository.deleteById(id); }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> actualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.actualizar(id, produto);
        return ResponseEntity.ok(produtoSalvo);
    }
}
