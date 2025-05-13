package com.teste_tecnico.clienteapi.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan
public class JSFConfig implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

    }
}
