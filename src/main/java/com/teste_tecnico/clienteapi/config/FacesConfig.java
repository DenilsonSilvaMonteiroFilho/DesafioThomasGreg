package com.teste_tecnico.clienteapi.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.faces.webapp.FacesServlet;

@Configuration
public class FacesConfig {
    @Bean
    public ServletRegistrationBean<FacesServlet> facesServlet() {
        ServletRegistrationBean<FacesServlet> servletRegistrationBean =
                new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }
}