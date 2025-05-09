package com.teste_tecnico.clienteapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Para facilitar testes com Postman
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic(); // Autenticacao HTTP básica (usuario/senha)
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admian")
                        .password("1234")
                        .roles("USER")
                        .build()
        );
    }

    // Para testes, sem encriptação (nao use em producao!)
    @Bean
    public static org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
