package com.teste_tecnico.clienteapi.procedures;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@Repository
public class ClienteProcedureRepository {

    @PersistenceContext
    private EntityManager entityManager;

    //Usar como base pra criacao de futuras procedures
    public void removerClientePorProcedure(Long id) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_remover_cliente");
        query.registerStoredProcedureParameter("id", Long.class, ParameterMode.IN);
        query.setParameter("id", id);
        query.execute();
    }
}