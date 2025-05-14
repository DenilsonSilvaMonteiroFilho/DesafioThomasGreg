package com.teste_tecnico.clienteapi.managedBeans;

import com.teste_tecnico.clienteapi.DTOs.LogradouroRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;

import com.teste_tecnico.clienteapi.DTOs.LogradouroDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@ViewScoped
public class LogradouroBean implements Serializable {

    private final String API_URL = "http://localhost:8080/api/logradouro";
    private final RestTemplate restTemplate = new RestTemplate();

    private Long clienteId;
    private List<LogradouroDTO> logradouros;
    private LogradouroRequestDTO novoLogradouro = new LogradouroRequestDTO();
    @PostConstruct
    public void init() {
        if (clienteId != null) {
            carregarLogradouros();
        }
    }

    public void carregarLogradouros() {
        try {
            String url = API_URL + "/cliente/" + clienteId; // Exemplo: /api/logradouro/cliente/1
            ResponseEntity<LogradouroDTO[]> response = restTemplate.getForEntity(url, LogradouroDTO[].class);
            logradouros = Arrays.asList(response.getBody());
        }catch(Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar logradouros", e.getMessage()));
        }
    }

    public void salvar() {
        try {
            //novoLogradouro.setClienteId(clienteId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<LogradouroRequestDTO> request = new HttpEntity<>(novoLogradouro, headers);

            restTemplate.postForEntity(API_URL, request, Void.class);

            carregarLogradouros(); // recarrega a lista após salvar
            novoLogradouro = new LogradouroRequestDTO(); // limpa formulário

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Logradouro salvo com sucesso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", e.getMessage()));
        }
    }

    // Getters e Setters

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
        carregarLogradouros();
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