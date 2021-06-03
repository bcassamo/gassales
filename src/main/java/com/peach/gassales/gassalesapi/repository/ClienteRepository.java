package com.peach.gassales.gassalesapi.repository;

import com.peach.gassales.gassalesapi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
