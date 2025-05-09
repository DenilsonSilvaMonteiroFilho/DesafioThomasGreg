package com.teste_tecnico.clienteapi.repositories;

import com.teste_tecnico.clienteapi.entities.Logradouro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogradouroRepository extends JpaRepository<Logradouro, Long> {
    List<Logradouro> findByClienteId(Long clienteId);
}
