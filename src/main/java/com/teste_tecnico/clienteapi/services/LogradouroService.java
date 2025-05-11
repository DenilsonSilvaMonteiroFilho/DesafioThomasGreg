package com.teste_tecnico.clienteapi.services;

import com.teste_tecnico.clienteapi.DTOs.LogradouroDTO;
import com.teste_tecnico.clienteapi.entities.Cliente;
import com.teste_tecnico.clienteapi.entities.Logradouro;
import com.teste_tecnico.clienteapi.repositories.ClienteRepository;
import com.teste_tecnico.clienteapi.repositories.LogradouroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LogradouroService {

    private final LogradouroRepository logradouroRepository;
    private final ClienteRepository clienteRepository;

    public LogradouroService(LogradouroRepository logradouroRepository, ClienteRepository clienteRepository) {
        this.logradouroRepository = logradouroRepository;
        this.clienteRepository = clienteRepository;
    }

    public LogradouroDTO criar(LogradouroDTO dto) {
        Logradouro logradouro = new Logradouro();
        logradouro.setEndereco(dto.getEndereco());
        logradouro.setCidade(dto.getCidade());
        logradouro.setUf(dto.getUf());

        Logradouro salvo = logradouroRepository.save(logradouro);
        return toDTO(salvo);
    }

    public List<LogradouroDTO> listarPorCliente(Long clienteId) {
        return logradouroRepository.findByClienteId(clienteId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public LogradouroDTO atualizar(Long id, LogradouroDTO dto) {
        Logradouro logradouro = logradouroRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Logradouro não encontrado"));

        logradouro.setEndereco(dto.getEndereco());
        logradouro.setCidade(dto.getCidade());
        logradouro.setUf(dto.getUf());

        return toDTO(logradouroRepository.save(logradouro));
    }

    public void remover(Long id) {
        logradouroRepository.deleteById(id);
    }

    public LogradouroDTO toDTO(Logradouro l) {
        LogradouroDTO dto = new LogradouroDTO();
        dto.setEndereco(l.getEndereco());
        dto.setCidade(l.getCidade());
        dto.setUf(l.getUf());
        return dto;
    }
}

