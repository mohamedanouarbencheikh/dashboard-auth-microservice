package com.github.gatewayservice.securityManagement;

import com.github.gatewayservice.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project gateway-service
 */
@Component
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    TokenService tokenService;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
           final Authentication[] newAuthentication = {null};
            return new ReactiveAuthenticationManager() {
                @Override
                public Mono<Authentication> authenticate(Authentication authentication) {
                    return Mono.fromCallable(() -> {
                        System.out.println(exchange.getRequest().getURI().getPath());
                        if (!exchange.getRequest().getURI().getPath().equals("/login-service/login")) {
                            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                            String username = tokenService.getUserName(token);
                            newAuthentication[0] = new UsernamePasswordAuthenticationToken(username, username);
                            List<String> roles = tokenService.getRoles(token);
                            Collection<GrantedAuthority> authorities = new ArrayList<>();
                            roles.forEach(rn -> {
                                authorities.add(new SimpleGrantedAuthority(rn));
                            });
                            UserEntity principal = new UserEntity();
                            principal.setUsername(username);
                            return new UsernamePasswordAuthenticationToken(principal, null, authorities);
                        }
                         else
                            return new UsernamePasswordAuthenticationToken(null, null, null);
                    });

                }

            }.authenticate(newAuthentication[0]).map(SecurityContextImpl::new);

        }



    }

