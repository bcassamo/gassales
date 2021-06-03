package com.peach.gassales.gassalesapi.resource;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.UnidadeMedida;
import com.peach.gassales.gassalesapi.repository.UnidadeMedidaRepository;
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
@RequestMapping("/unidades")
public class UnidadeMedidaResource {

    private UnidadeMedidaRepository unidadeMedidaRepository;
    private ApplicationEventPublisher publisher;

    @Autowired
    private void setUnidadeMedidaRepository(UnidadeMedidaRepository unidadeMedidaRepository) {
        this.unidadeMedidaRepository = unidadeMedidaRepository;
    }

    @Autowired
    private void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping
    public List<UnidadeMedida> listar() {
        return unidadeMedidaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<UnidadeMedida> criar(@Valid @RequestBody UnidadeMedida unidadeMedida, HttpServletResponse response) {
        UnidadeMedida unidadeMedidaSalva = unidadeMedidaRepository.save(unidadeMedida);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, unidadeMedidaSalva.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(unidadeMedidaSalva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeMedida> buscarPeloCodigo(@PathVariable Long id) {
        Optional<UnidadeMedida> unidadeMedida = unidadeMedidaRepository.findById(id);
        if(unidadeMedida.isEmpty())
            throw new EmptyResultDataAccessException(1);
        return unidadeMedida.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
