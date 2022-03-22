package com.github.gatewayservice.securityManagement;

import com.github.gatewayservice.business.UserBusiness;
import com.nimbusds.jose.*;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project gateway-service
 */
@Service
@PropertySource({"classpath:application.properties"})
public class TokenService {
	
	@Autowired
	UserBusiness utilisateurMetie;

	@Autowired
    private Environment env;

	private String secretKey;

	public static String tokenStart = "Bearer ";

	private byte[] secretKeyEncrypter;
	
	@PostConstruct
	public void start(){
		secretKey = env.getProperty("secret_key");
		secretKeyEncrypter = secretKey.getBytes();
	}
		
		public List<String> getRoles(String authorization){
			String token = authorization.substring(tokenStart.length());
			          
	        ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = new DefaultJWTProcessor<SimpleSecurityContext>();
	        JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<SimpleSecurityContext>(secretKeyEncrypter);
	        JWEKeySelector<SimpleSecurityContext> jweKeySelector = new JWEDecryptionKeySelector<SimpleSecurityContext>(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource);
	        jwtProcessor.setJWEKeySelector(jweKeySelector);
	        JWTClaimsSet claims = null;
			try {
				claims = jwtProcessor.process(token, null);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadJOSEException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JOSEException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return(List<String>)claims.getClaim("roles");
		}

		
		public String getUserName(String authorization){
			String token = authorization.substring(tokenStart.length());
	        ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = new DefaultJWTProcessor<SimpleSecurityContext>();
	        JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<SimpleSecurityContext>(secretKeyEncrypter);
	        JWEKeySelector<SimpleSecurityContext> jweKeySelector = new JWEDecryptionKeySelector<SimpleSecurityContext>(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource);
	        jwtProcessor.setJWEKeySelector(jweKeySelector);
	        JWTClaimsSet claims = null;
			try {
				claims = jwtProcessor.process(token, null);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadJOSEException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JOSEException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return claims.getSubject(); 
		}

	  private Date generateExpirationDate(int expirationPeriod) {
	      return new Date(System.currentTimeMillis() + expirationPeriod * 1000);
	  }

}
