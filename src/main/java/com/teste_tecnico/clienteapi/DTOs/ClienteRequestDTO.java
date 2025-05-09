package com.teste_tecnico.clienteapi.DTOs;

import com.teste_tecnico.clienteapi.entities.Logradouro;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ClienteRequestDTO {
    private String nome;
    private String email;
    private List<LogradouroDTO> logradouros;
    private MultipartFile logotipo;


    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public List<LogradouroDTO> getLogradouros() {
        return logradouros;
    }

    public MultipartFile getLogotipo() {
        return logotipo;
    }
}

