package com.teste_tecnico.clienteapi.DTOs;


import com.teste_tecnico.clienteapi.autorizacao.Role;

import java.util.List;

public class ClienteDTO {
    private Long id;
    private String nome;
    private String email;
    private Role role;
    private List<LogradouroDTO> logradouros;


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

    public List<LogradouroDTO> getLogradouros() {
        return logradouros;
    }

    public void setLogradouros(List<LogradouroDTO> logradouros) {
        this.logradouros = logradouros;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

