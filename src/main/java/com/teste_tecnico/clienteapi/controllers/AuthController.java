package com.teste_tecnico.clienteapi.controllers;

import com.teste_tecnico.clienteapi.DTOs.ClienteRequestDTO;
import com.teste_tecnico.clienteapi.autorizacao.Role;
import com.teste_tecnico.clienteapi.services.ClienteService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class AuthController {

    private final ClienteService clienteService;

    public AuthController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping("/login")
    public String login() {
        return "login.xhtml";
    }

    @GetMapping("/cadastro")
    public String cadastroForm() {
        return "cadastro.xhtml";
    }

    @PostMapping(name = "/cadastro", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String cadastrar(
            @RequestPart("dados") @Valid ClienteRequestDTO dto,
            @RequestPart("logotipo") MultipartFile logotipo) throws IOException {
        dto.setRole(Role.USER);
        clienteService.criarCliente(dto, logotipo);
        return "redirect:/login?cadastro=true";
    }

    @GetMapping("/erro-acesso-negado")
    public String acessoNegado() {
        return "erro-acesso-negado.xhtml";
    }
}
