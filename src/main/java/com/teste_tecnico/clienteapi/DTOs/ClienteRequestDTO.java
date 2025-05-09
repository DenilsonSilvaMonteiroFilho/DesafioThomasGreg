package com.teste_tecnico.clienteapi.DTOs;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ClienteRequestDTO {
    private String nome;
    private String email;
    private List<String> logradouros;
    private MultipartFile logotipo;
}

