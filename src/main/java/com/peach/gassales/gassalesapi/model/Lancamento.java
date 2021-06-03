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
@Table(name = "lancamento")
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String descricao;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_filial")
    private Filial filial;

    @NotNull
    @OneToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @NotNull
    private Long quantidade;

    @NotNull
    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoProduto estado;
}
