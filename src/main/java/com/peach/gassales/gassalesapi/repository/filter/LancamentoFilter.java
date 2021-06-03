package com.peach.gassales.gassalesapi.repository.filter;

import com.peach.gassales.gassalesapi.model.EstadoProduto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@Setter
public class LancamentoFilter {
    private String descricao;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataLancamentoDe;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataLancamentoAte;

    private EstadoProduto estado;
}
