package com.peach.gassales.gassalesapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Getter @Setter
@Table(name = "permissao")
public class Permissao {

    @Id
    private Long id;
    private String descricao;
}
