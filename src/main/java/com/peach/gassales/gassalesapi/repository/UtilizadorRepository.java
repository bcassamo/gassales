package com.peach.gassales.gassalesapi.repository;

import com.peach.gassales.gassalesapi.model.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {
    public Optional<Utilizador> findByEmail(String email);
}
