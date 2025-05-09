package com.teste_tecnico.clienteapi.repositories;

import org.aspectj.apache.bcel.generic.InstructionConstants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<InstructionConstants.Clinit, Long> {
}
