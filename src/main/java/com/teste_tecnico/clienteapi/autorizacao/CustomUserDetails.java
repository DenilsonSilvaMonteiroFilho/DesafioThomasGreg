package com.teste_tecnico.clienteapi.autorizacao;

import com.teste_tecnico.clienteapi.entities.Cliente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final Cliente cliente;

    public CustomUserDetails(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + cliente.getRole().name()));

    }

    @Override
    public String getPassword() {
        return cliente.getEmail(); // email como "senha" (por simplificação)
    }

    @Override
    public String getUsername() {
        return cliente.getNome();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public Long getId() {
        return cliente.getId();
    }
}