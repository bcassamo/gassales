package com.peach.gassales.gassalesapi.resource;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.Filial;
import com.peach.gassales.gassalesapi.repository.FilialRepository;
import com.peach.gassales.gassalesapi.service.FilialService;
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
@RequestMapping("/filial")
public class FilialResource {

    private FilialRepository filialRepository;
    private ApplicationEventPublisher publisher;
    private FilialService filialService;

    @Autowired
    private void setFilialRepository(FilialRepository filialRepository) {
        this.filialRepository = filialRepository;
    }

    @Autowired
    private void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Autowired
    private void  setFilialService(FilialService filialService) {
        this.filialService = filialService;
    }

    @GetMapping
    public List<Filial> listar() {
        return filialRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Filial> criar(@Valid @RequestBody Filial filial, HttpServletResponse response) {
        Filial filialSalva = filialRepository.save(filial);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, filialSalva.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(filialSalva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filial> buscarPeloCodigo(@PathVariable Long id) {
        Optional<Filial> filial = filialService.buscarFilialPeloCodigo(id);
        return filial.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        filialRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filial> actualizar(@PathVariable Long id, @Valid @RequestBody Filial filial) {
        Filial filialSalva = filialService.actualizar(id, filial);
        return ResponseEntity.ok(filialSalva);
    }
}
