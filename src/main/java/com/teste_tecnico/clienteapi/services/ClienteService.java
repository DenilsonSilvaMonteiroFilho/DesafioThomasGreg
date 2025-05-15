package com.teste_tecnico.clienteapi.services;

import com.teste_tecnico.clienteapi.DTOs.ClienteDTO;
import com.teste_tecnico.clienteapi.DTOs.ClienteRequestDTO;
import com.teste_tecnico.clienteapi.autorizacao.Role;
import com.teste_tecnico.clienteapi.entities.Cliente;
import com.teste_tecnico.clienteapi.entities.Logradouro;
import com.teste_tecnico.clienteapi.repositories.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final LogradouroService logradouroService;

    public ClienteService(ClienteRepository clienteRepository,
                          LogradouroService logradouroService) {
        this.clienteRepository = clienteRepository;
        this.logradouroService = logradouroService;
    }

    @Transactional
    public ClienteDTO criarCliente(ClienteRequestDTO dto, MultipartFile logotipo) throws IOException {
        Cliente existente = clienteRepository.findByEmail(dto.getEmail());
        if (existente!=null) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setRole(Role.USER);

        if (logotipo != null && !logotipo.isEmpty()) {
            cliente.setLogotipo(logotipo.getBytes());
        }
        if(cliente.getLogradouros()!=null&&!cliente.getLogradouros().isEmpty()) {
            List<Logradouro> logradouros = dto.getLogradouros().stream()
                    .map(logradouroDTO -> {
                        Logradouro logradouro = new Logradouro();
                        logradouro.setEndereco(logradouroDTO.getEndereco());
                        logradouro.setCidade(logradouroDTO.getCidade());
                        logradouro.setUf(logradouroDTO.getUf());
                        logradouro.setCliente(cliente);
                        return logradouro;
                    })
                    .collect(Collectors.toList());
            cliente.setLogradouros(logradouros);
        }

        return toDTO(clienteRepository.save(cliente));
    }

    @Transactional
    public ResponseEntity alterarDados(Long idCliente,ClienteRequestDTO clienteRequestDTO){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));
        cliente.setNome(clienteRequestDTO.getNome());
        cliente.setEmail(clienteRequestDTO.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(toDTO(clienteRepository.save(cliente)));
    }

    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removerCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));
        return toDTO(cliente);
    }

    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setLogradouros(cliente.getLogradouros().stream()
                .map(logradouro -> {
                return logradouroService.toDTO(logradouro);
                })
                .collect(Collectors.toList()));
        return dto;
    }
}

