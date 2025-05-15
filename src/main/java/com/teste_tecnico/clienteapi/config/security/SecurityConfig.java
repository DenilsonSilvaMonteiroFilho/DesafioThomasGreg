package com.teste_tecnico.clienteapi.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/pages/publico/**").permitAll()
                .antMatchers("/pages/admin/**").hasRole("ADMIN")
                .antMatchers("/pages/cliente/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/pages/publico/login.xhtml")
                .defaultSuccessUrl("/pages/cliente/home", true)
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/pages/publico/erro-acesso-negado.xhtml");

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
