package com.peach.gassales.gassalesapi.resource;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.Produto;
import com.peach.gassales.gassalesapi.model.Stock;
import com.peach.gassales.gassalesapi.repository.ProdutoRepository;
import com.peach.gassales.gassalesapi.repository.filter.ProdutoFilter;
import com.peach.gassales.gassalesapi.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    public Page<Produto> pesquisar(ProdutoFilter produtoFilter, Pageable pageable) { return produtoRepository.filtrar(produtoFilter, pageable); }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPeloCodigo(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarProdutoPeloCodigo(id);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Stock> buscarStockDoProduto(@PathVariable Long id) {
        Optional<Stock> stock = produtoService.buscarStockDoProduto(id);
        return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
