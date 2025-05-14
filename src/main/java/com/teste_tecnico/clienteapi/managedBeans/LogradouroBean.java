package com.teste_tecnico.clienteapi.managedBeans;

import com.teste_tecnico.clienteapi.DTOs.ClienteDTO;
import com.teste_tecnico.clienteapi.DTOs.LogradouroRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import javax.faces.view.ViewScoped;
import com.teste_tecnico.clienteapi.DTOs.LogradouroDTO;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Component
@ViewScoped
public class LogradouroBean implements Serializable {

    private final String API_URL = "http://localhost:8080/api/logradouro";
    private final RestTemplate restTemplate = new RestTemplate();

    private Long clienteId;

    private ClienteDTO clienteDTO;
    private List<LogradouroDTO> logradouros;
    private LogradouroRequestDTO novoLogradouro = new LogradouroRequestDTO();
    private Long logradouroIdAlterar;
    private LogradouroRequestDTO alterarLogradouro = new LogradouroRequestDTO();
    private Long logradouroIdExcluir;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        String clienteIdParam = context.getExternalContext().getRequestParameterMap().get("clienteId");
        if (clienteIdParam != null) {
            this.clienteId = Long.parseLong(clienteIdParam);
            carregarLogradouros();
        }
    }

    public void carregarLogradouros() {
        try {
            //Deve ter uma fomar melhor de so pegar o nome do cliente, ou passar os dados do cliente para essa tela
            String urlCliente = "http://localhost:8080/api/clientes/" + clienteId;
            String url = API_URL + "/cliente/" + clienteId;
            ResponseEntity<ClienteDTO> responseCleinte = restTemplate.getForEntity(urlCliente, ClienteDTO.class);
            ResponseEntity<LogradouroDTO[]> response = restTemplate.getForEntity(url, LogradouroDTO[].class);
            logradouros = Arrays.asList(response.getBody());
            clienteDTO = responseCleinte.getBody();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar logradouros", e.getMessage()));
        }
    }

    public void salvar() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String url = API_URL + "/cliente/" + clienteId;

            HttpEntity<LogradouroRequestDTO> request = new HttpEntity<>(novoLogradouro, headers);

            restTemplate.postForEntity(url, request, Void.class);

            carregarLogradouros(); // recarrega a lista apos salvar
            novoLogradouro = new LogradouroRequestDTO(); // limpa formulario

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Logradouro salvo com sucesso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", e.getMessage()));
        }
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
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

    public void setLogradouros(List<LogradouroDTO> logradouros) {
        this.logradouros = logradouros;
    }

    public Long getLogradouroIdAlterar() {
        return logradouroIdAlterar;
    }

    public void setLogradouroIdAlterar(Long logradouroIdAlterar) {
        this.logradouroIdAlterar = logradouroIdAlterar;
    }

    public LogradouroRequestDTO getAlterarLogradouro() {
        return alterarLogradouro;
    }

    public void setAlterarLogradouro(LogradouroDTO logradouroAlterar) {
        this.alterarLogradouro.setCidade(logradouroAlterar.getCidade());
        this.alterarLogradouro.setEndereco(logradouroAlterar.getEndereco());
        this.alterarLogradouro.setUf(logradouroAlterar.getUf());
    }

    public Long getLogradouroIdExcluir() {
        return logradouroIdExcluir;
    }

    public void setLogradouroIdExcluir(Long logradouroIdExcluir) {
        this.logradouroIdExcluir = logradouroIdExcluir;
    }

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }
}