package com.peach.gassales.gassalesapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "id_lancamento")
    private Lancamento lancamento;

    @NotNull
    @OneToOne
    @JoinColumn(name = "id_entidade")
    private Entidade entidade;
}
