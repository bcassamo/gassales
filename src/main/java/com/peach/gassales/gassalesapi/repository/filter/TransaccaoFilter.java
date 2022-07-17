package com.peach.gassales.gassalesapi.repository.filter;

import com.peach.gassales.gassalesapi.model.Entidade;
import com.peach.gassales.gassalesapi.model.EstadoProduto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@Setter
public class TransaccaoFilter {

    private String idTransaccao;

    private String tipo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataTransaccaoDe;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataTransaccaoAte;

    @Enumerated(EnumType.STRING)
    private EstadoProduto estado;
}
