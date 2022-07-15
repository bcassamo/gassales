package com.peach.gassales.gassalesapi.resource;

import com.peach.gassales.gassalesapi.model.Business;
import com.peach.gassales.gassalesapi.model.Lancamento;
import com.peach.gassales.gassalesapi.repository.LancamentoRepository;
import com.peach.gassales.gassalesapi.repository.filter.LancamentoFilter;
import com.peach.gassales.gassalesapi.service.BusinessService;
import com.peach.gassales.gassalesapi.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
    private LancamentoRepository lancamentoRepository;
    private LancamentoService lancamentoService;
    private BusinessService businessService;

    @Autowired
    private void setLancamentoRepository(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    @Autowired
    private void setLancamentoService(LancamentoService lancamentoService) {
        this.lancamentoService = lancamentoService;
    }

    @Autowired
    private void setBusinessService(BusinessService businessService) {
        this.businessService = businessService;
    }


    @GetMapping
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
        return lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> buscarLancamentoPeloCodigo(@PathVariable Long id) {
        Optional<Lancamento> lancamento = lancamentoService.buscarLancamentoPeloCodigo(id);
        return lancamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        lancamento.setCodigoBusiness(businessService.gerarBusinessCode(lancamento.getDescricao()));
        Lancamento lancamentoSalvo = lancamentoService.novoLancamento(lancamento, this, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @PostMapping("/business")
    public ResponseEntity<Business> criarBusiness(@Valid @RequestBody Business business, HttpServletResponse response) {
        // Business business = new Business();
        // Lancamento lancamentoSalvo = lancamentoService.novoLancamento(lancamento, this, response);
        Business businessSalvo = businessService.newBusiness(business, this, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(businessSalvo);
    }

    @PutMapping("/business/{codigoBusiness}/finalizado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void finalizarBusiness(@PathVariable String codigoBusiness, @RequestBody boolean finalizado){
        businessService.finalizarBusiness(codigoBusiness, finalizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        lancamentoRepository.deleteById(id);
    }
}
