package com.teste_tecnico.clienteapi.DTOs;

import com.teste_tecnico.clienteapi.entities.Logradouro;

import java.util.List;

public class ClienteDTO {
    private Long id;
    private String nome;
    private String email;
    private List<Logradouro> logradouros;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Logradouro> getLogradouros() {
        return logradouros;
    }

    public void setLogradouros(List<Logradouro> logradouros) {
        this.logradouros = logradouros;
    }
}

