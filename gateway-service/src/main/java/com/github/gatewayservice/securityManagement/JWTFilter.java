package com.github.gatewayservice.securityManagement;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project gateway-service
 */
@Component
@PropertySource({"classpath:application.properties"})
public class JWTFilter implements WebFilter {

    @Autowired
    private Environment env;

    public static HashMap<String, Date> tokenToDelete = new HashMap<>();
    private static String contextServer = "";
    private String secretKey;
    private DirectEncrypter encrypter;

    @PostConstruct
    public void start() {
        secretKey = env.getProperty("secret_key");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String token = "";

        JWTClaimsSet claims;

        if (exchange.getRequest().getMethod().equals("OPTIONS")) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);

        } else if(!exchange.getRequest().getURI().getPath().startsWith("/login-service")){
            try {

                token = exchange.getRequest().getHeaders().getFirst("Authorization");
                if (token == null) {
                    throw new Exception();
                }
            } catch (Exception e) {
                return Mono.empty();
            }
        }
        return chain.filter(exchange);
    }
}
