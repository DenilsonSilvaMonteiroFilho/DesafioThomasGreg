package com.teste_tecnico.clienteapi.managedBeans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste_tecnico.clienteapi.DTOs.ClienteDTO;
import com.teste_tecnico.clienteapi.DTOs.ClienteRequestDTO;
import org.primefaces.model.file.UploadedFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Component
@ViewScoped
public class ClienteBean implements Serializable {

    private List<ClienteDTO> clientes;
    private ClienteRequestDTO novoCliente = new ClienteRequestDTO();
    private UploadedFile logotipo;
    private Long clienteIdParaExcluir;
    private ClienteRequestDTO clienteAlterarDados = new ClienteRequestDTO();
    private Long clienteIdParaAlterar;



    private final String API_URL = "http://localhost:8080/api/clientes";

    @PostConstruct
    public void init() {
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
            String clienteJson = new ObjectMapper().writeValueAsString(novoCliente);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            // Parte JSON (Cliente)
            HttpHeaders jsonHeaders = new HttpHeaders();
            jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> clientePart = new HttpEntity<>(clienteJson, jsonHeaders);
            body.add("dados", clientePart);


            // Parte do logotipo
            body.add("logotipo","");//previnir quando usuario colocar imagem
            //COLOCAR UMA IMAGEM DEFULT PARA QUANDO NAO CADASTRAR IMAGEM
            if (logotipo != null && logotipo.getContent() != null) {
                Resource logoResource = new ByteArrayResource(logotipo.getContent()) {
                    @Override
                    public String getFilename() {
                        return logotipo.getFileName();
                    }

                    @Override
                    public long contentLength() {
                        return logotipo.getContent().length;
                    }
                };

                HttpHeaders fileHeaders = new HttpHeaders();
                fileHeaders.setContentType(MediaType.parseMediaType(logotipo.getContentType()));
                fileHeaders.setContentDispositionFormData("logotipo", logotipo.getFileName());

                HttpEntity<Resource> filePart = new HttpEntity<>(logoResource, fileHeaders);
                body.set("logotipo",filePart);
            }

            // Cabeçalhos gerais
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setAccessControlRequestMethod(HttpMethod.POST);

            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(API_URL, request, String.class);

            // Redireciona para lista
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("cliente_list.xhtml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(Long id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = API_URL + "/" + id;

            restTemplate.delete(url);

            // Atualiza lista após exclusão
            init();
            carregarClientes();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente excluído com sucesso!"));
        } catch (HttpClientErrorException.NotFound e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Cliente não encontrado."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao excluir cliente."));
            e.printStackTrace();
        }
    }

    public void alterar(Long id){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = API_URL + "/" + id;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Cria o corpo da requisição com os dados atualizados (nome e email)
            HttpEntity<ClienteRequestDTO> requestEntity = new HttpEntity<>(clienteAlterarDados, headers);

            restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Dados do cliente alterados com sucesso!"));

            carregarClientes(); // Atualiza a lista

        } catch (HttpClientErrorException.NotFound e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Cliente não encontrado."));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao alterar dados do cliente."));
        }
    }

    public String redirecionarParaLogradouros(Long clienteId) {
        return "logradouro.xhtml?faces-redirect=true&amp;clienteId=" + clienteId;
    }

    public List<ClienteDTO> getClientes() { return clientes; }
    public ClienteRequestDTO getNovoCliente() { return novoCliente; }
    public void setNovoCliente(ClienteRequestDTO novoCliente) { this.novoCliente = novoCliente; }
    public UploadedFile getLogotipo() { return logotipo; }
    public void setLogotipo(UploadedFile logotipo) { this.logotipo = logotipo; }
    public Long getClienteIdParaExcluir() {
        return clienteIdParaExcluir;
    }
    public void setClienteIdParaExcluir(Long clienteIdParaExcluir) {
        this.clienteIdParaExcluir = clienteIdParaExcluir;
    }

    public void setClientes(List<ClienteDTO> clientes) {
        this.clientes = clientes;
    }

    public ClienteRequestDTO getClienteAlterarDados() {
        return clienteAlterarDados;
    }

    public void setClienteAlterarDados(ClienteDTO clienteAlterarDados) {
        this.clienteAlterarDados.setNome(clienteAlterarDados.getNome());
        this.clienteAlterarDados.setEmail(clienteAlterarDados.getEmail());
    }

    public Long getClienteIdParaAlterar() {
        return clienteIdParaAlterar;
    }

    public void setClienteIdParaAlterar(Long clienteIdParaAlterar) {
        this.clienteIdParaAlterar = clienteIdParaAlterar;
    }
}

