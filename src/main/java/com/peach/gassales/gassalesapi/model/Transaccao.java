package com.peach.gassales.gassalesapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "transaccao")
public class Transaccao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_transaccao")
    private String idTransaccao;

    @NotNull
    @Size(min = 3, max = 20)
    private String tipo;

    @NotNull
    @Column(name = "data_transaccao")
    private LocalDate dataTransaccao;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_entidade")
    private Entidade entidade;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_produto")
    private EstadoProduto estado;

    @NotNull
    private Long quantidade;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;
}
