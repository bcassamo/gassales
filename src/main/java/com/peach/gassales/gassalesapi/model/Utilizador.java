package com.peach.gassales.gassalesapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "utilizador")
public class Utilizador {

    @Id
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "utilizador_permissao", joinColumns = @JoinColumn(name = "id_utilizador"),
            inverseJoinColumns = @JoinColumn(name = "id_permissao"))
    private List<Permissao> permissoes;
}
