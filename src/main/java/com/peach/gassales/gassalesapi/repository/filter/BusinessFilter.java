package com.peach.gassales.gassalesapi.repository.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class BusinessFilter {
    private String codigoBusiness;

    private String descricao;

    //private Long idEntidade;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataBusinessDe;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataBusinessAte;
}
