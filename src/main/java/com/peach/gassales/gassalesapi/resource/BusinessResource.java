package com.peach.gassales.gassalesapi.resource;

import com.peach.gassales.gassalesapi.model.Business;
import com.peach.gassales.gassalesapi.repository.BusinessRepository;
import com.peach.gassales.gassalesapi.repository.filter.BusinessFilter;
import com.peach.gassales.gassalesapi.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/business")
public class BusinessResource {
    private BusinessRepository businessRepository;
    private BusinessService businessService;

    @Autowired
    private void setBusinessRepository(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Autowired
    private void setBusinessService(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    public Page<Business> pesquisar(BusinessFilter businessFilter, Pageable pageable) {
        return businessRepository.filtrar(businessFilter, pageable);
    }

    // No need this methot o filtrar ja tem codigo do Business
    /*@GetMapping("/{codigoBusiness}")
    public ResponseEntity<Business> buscarBusinessesPeloCodigo(@PathVariable String codigoBusiness) {
        Optional<Business> business = businessService.buscarBusinessPeloCodigo(codigoBusiness);
        return business.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }*/

    // Alterei a tabela produto para adicionar preco
    //@PostMapping("/aquisicao")
    //public ResponseEntity<Business> criar(@Valid @RequestBody Business business, HttpServletResponse response) {
        //Business businessSalvo = BusinessService.novaAquisicao(business, this, response);
        //return ResponseEntity.status(HttpStatus.CREATED).body(businessSalvo);
    //}

    //@PostMapping
    //public ResponseEntity<Business> criar(@Valid @RequestBody Business business, HttpServletResponse response) {

        //Business businessSalvo = businessService.novoBusiness(business, this, response);
//        if(lancamento.getBusiness() == null) {
//            Business business = businessService.generateNewBusiness(lancamento.getDescricao());
//            lancamento.setBusiness(business);
//        }
        //Lancamento lancamentoSalvo = lancamentoService.novoLancamento(lancamento, this, response);
        //return ResponseEntity.status(HttpStatus.CREATED).body(businessSalvo);
    //}
}
