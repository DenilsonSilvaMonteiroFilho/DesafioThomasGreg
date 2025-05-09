package com.teste_tecnico.clienteapi.repositories;

import com.teste_tecnico.clienteapi.entities.Cliente;
import org.aspectj.apache.bcel.generic.InstructionConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<InstructionConstants.Clinit, Long> {
    boolean existsByEmail(String email); // para validacao de unicidade quando for cadastrar
}
