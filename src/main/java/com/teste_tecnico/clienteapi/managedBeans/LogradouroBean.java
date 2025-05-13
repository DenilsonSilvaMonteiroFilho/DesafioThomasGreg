package com.teste_tecnico.clienteapi.managedBeans;

import com.teste_tecnico.clienteapi.DTOs.LogradouroRequestDTO;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;

import com.teste_tecnico.clienteapi.DTOs.LogradouroDTO;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class LogradouroBean implements Serializable {

    private Long clienteId;
    private List<LogradouroDTO> logradouros = new ArrayList<>();
    private LogradouroRequestDTO novoLogradouro = new LogradouroRequestDTO();

    @PostConstruct
    public void init() {
        if (clienteId != null) {
            carregarLogradouros();
        }
    }

    public void carregarLogradouros() {
        // Chamada para a API REST com clienteId
        // Exemplo (mock):
        logradouros = new ArrayList<>();
        logradouros.add(new LogradouroDTO("Rua A", "São Paulo", "SP"));
    }

    public void salvar() {
       // novoLogradouro.setClienteId(clienteId);
        // Enviar novoLogradouro para API REST
        // Depois:
        carregarLogradouros();
        novoLogradouro = new LogradouroRequestDTO();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Logradouro salvo com sucesso"));
    }

    // Getters e Setters

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
        carregarLogradouros(); // carregamento imediato ao setar
    }

    public List<LogradouroDTO> getLogradouros() {
        return logradouros;
    }

    public LogradouroRequestDTO getNovoLogradouro() {
        return novoLogradouro;
    }

    public void setNovoLogradouro(LogradouroRequestDTO novoLogradouro) {
        this.novoLogradouro = novoLogradouro;
    }
}