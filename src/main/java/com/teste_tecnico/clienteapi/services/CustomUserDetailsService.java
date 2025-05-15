package com.teste_tecnico.clienteapi.services;

import com.teste_tecnico.clienteapi.autorizacao.CustomUserDetails;
import com.teste_tecnico.clienteapi.entities.Cliente;
import com.teste_tecnico.clienteapi.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByNome(nome)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return new CustomUserDetails(cliente);
    }
}