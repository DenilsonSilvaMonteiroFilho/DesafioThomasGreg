package com.teste_tecnico.clienteapi.repositories;

import com.teste_tecnico.clienteapi.entities.Logradouro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogradouroRepository extends JpaRepository<Logradouro, Long> {
}
