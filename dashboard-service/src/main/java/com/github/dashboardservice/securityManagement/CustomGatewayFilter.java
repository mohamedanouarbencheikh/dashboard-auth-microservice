package com.github.dashboardservice.securityManagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project dashboard-service
 */
@Component
@PropertySource({"classpath:application.properties"})
public class CustomGatewayFilter extends GenericFilterBean {

    @Autowired
    private Environment env;

    private String ipGateway;
    private int portGateway;

    @PostConstruct
    public void start() {

        ipGateway = env.getProperty("ip_gateway");
        portGateway = env.getProperty("port_gateway", Integer.class);
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String proxyForwardedHostHeader = request.getHeader("X-Forwarded-Host");
       if (proxyForwardedHostHeader == null || !proxyForwardedHostHeader.equals("localhost:9999")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "acces not allowed");
            return;
        }
        chain.doFilter(request, response);
    }

}
