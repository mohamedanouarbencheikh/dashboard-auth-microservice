package com.github.gatewayservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project gateway-service
 */
@Component
@PropertySource({"classpath:application.properties"})
public class ServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Autowired
    private  Environment env;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.setPort(env.getProperty("port_gateway", Integer.class));
    }
}
