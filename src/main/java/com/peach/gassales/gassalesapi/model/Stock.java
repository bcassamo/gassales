package com.peach.gassales.gassalesapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long quantidade;

    @NotNull
    @OneToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @NotNull
    @OneToOne
    @JoinColumn(name = "id_filial")
    private Filial filial;
}
