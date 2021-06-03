package com.peach.gassales.gassalesapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Endereco {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String provincia;
}
