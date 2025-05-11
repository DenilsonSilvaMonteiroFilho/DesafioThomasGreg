package com.teste_tecnico.clienteapi.repositories;

import com.teste_tecnico.clienteapi.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByEmail(String email); // para validacao de unicidade quando for cadastrar
}
