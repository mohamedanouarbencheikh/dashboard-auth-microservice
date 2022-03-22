package com.github.loginservice.securityManagement;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import io.jsonwebtoken.SignatureException;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Component
@PropertySource({"classpath:application.properties"})
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private Environment env;

    @Autowired
    MyLogoutSuccessHandler myLogoutSuccessHandler;

    private String secretKey;

    @PostConstruct
    public void start() {
        secretKey = env.getProperty("secret_key");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = "";
        JWTClaimsSet claims;
        String userName;
        List<String> roles;

        String proxyForwardedHostHeader = request.getHeader("X-Forwarded-Host");


        if (proxyForwardedHostHeader == null || !proxyForwardedHostHeader.equals("localhost:9999")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "acces not allowed");
            return;
        }
        else if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println(request.getRequestURI());
             } else if (request.getRequestURI().equals("/login")) {
            filterChain.doFilter(request, response);
            return;
        } else {
            try {
                token = request.getHeader("Authorization");
                if (token == null) {
                    throw new Exception();
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                new Exception("Invalid token");
                System.out.println(request.getRequestURI());
                logger.fatal("Invalid token");
                return;
            }
            try {
                if (!token.startsWith(TokenService.tokenStart)) {
                    throw new Exception();
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                new Exception("Invalid token");
                System.out.println(request.getRequestURI());
                logger.fatal("Invalid token");
                return;
            }

            try {
                token = token.substring(TokenService.tokenStart.length());
                byte[] secretKeyEncrypter = secretKey.getBytes();
                ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = new DefaultJWTProcessor<SimpleSecurityContext>();
                JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<SimpleSecurityContext>(secretKeyEncrypter);
                JWEKeySelector<SimpleSecurityContext> jweKeySelector = new JWEDecryptionKeySelector<SimpleSecurityContext>(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource);
                jwtProcessor.setJWEKeySelector(jweKeySelector);
                claims = jwtProcessor.process(token, null);

                userName = claims.getSubject();

                roles = (List<String>) claims.getClaim("roles");
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                roles.forEach(rn -> {
                    authorities.add(new SimpleGrantedAuthority(rn));
                });
                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userName, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(user);

            } catch (SignatureException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                new Exception("token with invalid secret key");
                System.out.println(request.getRequestURI());
                logger.fatal("token with invalid secret key");
                return;
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                new Exception("token expired");
                System.out.println(request.getRequestURI());
                logger.fatal("token expired");
                return;
            }

            try {
                filterChain.doFilter(request, response);
            } catch (Exception e) {
            }

        }
    }


}
