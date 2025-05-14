package com.teste_tecnico.clienteapi.controllers;

import com.teste_tecnico.clienteapi.DTOs.LogradouroDTO;
import com.teste_tecnico.clienteapi.entities.Cliente;
import com.teste_tecnico.clienteapi.services.LogradouroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/logradouro")
@CrossOrigin(origins = "*")
public class LogradouroController {

    private final LogradouroService logradouroService;

    public LogradouroController(LogradouroService logradouroService) {
        this.logradouroService = logradouroService;
    }

    @PostMapping("/cliente/{idCliente}")
    public ResponseEntity<String> adicionarLogradouro(@PathVariable Long idCliente,
                                                      @RequestBody @Valid LogradouroDTO dto) {
        Cliente cliente = logradouroService.addLagradouro(idCliente, dto);
        return ResponseEntity.ok("Logradouro adicionado ao cliente ID: " + cliente.getId());
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<LogradouroDTO>> listarPorCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(logradouroService.listarPorCliente(idCliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LogradouroDTO> atualizarLogradouro(@PathVariable Long id,
                                                             @RequestBody @Valid LogradouroDTO dto) {
        return ResponseEntity.ok(logradouroService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerLogradouro(@PathVariable Long id) {
        logradouroService.remover(id);
        return ResponseEntity.noContent().build();
    }
}

