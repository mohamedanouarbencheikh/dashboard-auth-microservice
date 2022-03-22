package com.github.gatewayservice.microserviceAccess;

import com.github.gatewayservice.securityManagement.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project gateway-service
 */
@Component
public class DashboardServiceAccess implements ReactiveAuthorizationManager<AuthorizationContext> {


    @Autowired
    TokenService tokenService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    HashMap<String,List<String>> rolesByRequest = new HashMap<>();
    @PostConstruct
    public void start(){
        System.out.println("start DashboardServiceAccess");
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        rolesByRequest.put("/app/loadDataDashboard/**",roles);
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        String requestUrl = object.getExchange().getRequest().getPath().pathWithinApplication().value();
        List<String> roles = new ArrayList<>();
        rolesByRequest.keySet().forEach(m -> {
            if (antPathMatcher.match("/app/dashboard-service"+m, requestUrl)) {
                List<String> allRoleByMenuId = rolesByRequest.get(m)
                        .stream()
                        .collect(Collectors.toList());
                roles.addAll(allRoleByMenuId);
            }
        });

        if (roles.isEmpty()) {
            return Mono.just(new AuthorizationDecision(false));
        }
        return authentication
                .filter(a -> a.isAuthenticated())
                .flatMapIterable(a -> a.getAuthorities())
                .map(g -> g.getAuthority())
                .any(c -> {
                    if (roles.contains(String.valueOf(c))) {
                        return true;
                    }
                    return false;
                })
                .map(hasAuthority -> new AuthorizationDecision(hasAuthority))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return null;
    }

}
