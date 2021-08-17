package com.peach.gassales.gassalesapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoBusiness;

    @NotNull
    @Size(min = 3, max = 20)
    private String descricao;

    @NotNull
    @Column(name = "data_business")
    private LocalDate dataBusiness;

//    @NotNull
//    @Column(name = "valor_business")
//    private BigDecimal valorBusiness;

    @ManyToOne
    @JoinColumn(name = "id_lancamento")
    private Lancamento lancamento;
}
