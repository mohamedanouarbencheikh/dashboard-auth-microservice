package com.github.gatewayservice.securityManagement;
import com.github.gatewayservice.microserviceAccess.DashboardServiceAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project gateway-service
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig{
    @Autowired
    JWTFilter jWTFilter;

    @Autowired
    JwtSecurityContextRepository jwtSecurityContextRepository;

    @Autowired
    DashboardServiceAccess dashboardServiceAccess;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http.cors().and().csrf().disable();
        http.authorizeExchange()
                .pathMatchers("/dashboard-service/**").permitAll()
                .pathMatchers("/login-service/**").permitAll()

                .pathMatchers("/dashboard-service/**").access(dashboardServiceAccess)
                .and()
               // .securityContextRepository(jwtSecurityContextRepository)
                .addFilterAt(jWTFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); //or add * to allow all origins
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); //to set allowed http methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("custom-header1", "custom-header2"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
