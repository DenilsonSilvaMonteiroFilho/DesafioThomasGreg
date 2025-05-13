package com.teste_tecnico.clienteapi.managedBeans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste_tecnico.clienteapi.DTOs.ClienteDTO;
import com.teste_tecnico.clienteapi.DTOs.ClienteRequestDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Component
@ViewScoped
public class ClienteBean implements Serializable {

    private List<ClienteDTO> clientes;
    private ClienteRequestDTO novoCliente = new ClienteRequestDTO();
    private Part logotipo;

    private final String API_URL = "http://localhost:8080/api/clientes";

    @PostConstruct
    public void init() {
        carregarClientes();
    }

    public void carregarClientes() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ClienteDTO[] array = restTemplate.getForObject(API_URL, ClienteDTO[].class);
            this.clientes = Arrays.asList(array);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salvar() {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Converte DTO em JSON
            String clienteJson = new ObjectMapper().writeValueAsString(novoCliente);

            // Converte imagem
            InputStream is = logotipo.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[] bytes = buffer.toByteArray();
            ByteArrayResource logoResource = new ByteArrayResource(bytes) {
                @Override
                public String getFilename() {
                    return logotipo.getSubmittedFileName();
                }
            };

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("cliente", clienteJson);
            body.add("logotipo", logoResource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

            restTemplate.postForEntity(API_URL, request, String.class);

            // Redireciona para lista
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("cliente.xhtml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getters e Setters
    public List<ClienteDTO> getClientes() { return clientes; }
    public ClienteRequestDTO getNovoCliente() { return novoCliente; }
    public void setNovoCliente(ClienteRequestDTO novoCliente) { this.novoCliente = novoCliente; }
    public Part getLogotipo() { return logotipo; }
    public void setLogotipo(Part logotipo) { this.logotipo = logotipo; }
}

