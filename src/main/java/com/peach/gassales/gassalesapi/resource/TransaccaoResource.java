package com.peach.gassales.gassalesapi.resource;

import com.peach.gassales.gassalesapi.model.Transaccao;
import com.peach.gassales.gassalesapi.repository.TransaccaoRepository;
import com.peach.gassales.gassalesapi.repository.filter.TransaccaoFilter;
import com.peach.gassales.gassalesapi.service.TransaccaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/transaccao")
public class TransaccaoResource {
    private TransaccaoRepository transaccaoRepository;

    private TransaccaoService transaccaoService;

    @Autowired
    public void setTransaccaoRepository(TransaccaoRepository transaccaoRepository) {
        this.transaccaoRepository = transaccaoRepository;
    }

    @Autowired
    public void setTransaccaoService(TransaccaoService transaccaoService) {
        this.transaccaoService = transaccaoService;
    }

    @GetMapping
    public Page<Transaccao> pesquisar(TransaccaoFilter transaccaoFilter, Pageable pageable) {
        return transaccaoRepository.filtrar(transaccaoFilter, pageable);
    }

    @PostMapping
    public ResponseEntity<Transaccao> criar(@Valid @RequestBody Transaccao transaccao, HttpServletResponse response) {
        transaccao.setIdTransaccao(transaccaoService.gerarIdtransaccao(transaccao.getTipo()));
        Transaccao transaccaoSalva = transaccaoService.novaTransaccao(transaccao, this, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaccaoSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        transaccaoRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaccao> actualizar(@PathVariable Long id, @Valid @RequestBody Transaccao transaccao) {
        Transaccao transaccaoSalva = transaccaoService.actualizar(id, transaccao);
        return ResponseEntity.ok(transaccaoSalva);
    }
}
