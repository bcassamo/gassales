package com.peach.gassales.gassalesapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String nome;

    @Size(min = 3, max = 255)
    private String descricao;

    @Size(min = 3, max = 20)
    private String referencia;

    @NotNull
    private BigDecimal tamanho;

    @NotNull
    private BigDecimal preco;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_tipo_produto")
    private TipoProduto tipoProduto;
}
