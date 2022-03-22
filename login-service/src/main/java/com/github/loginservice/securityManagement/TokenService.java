package com.github.loginservice.securityManagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import com.github.loginservice.business.UserBusiness;
import com.github.loginservice.models.Role;
import com.github.loginservice.models.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jwt.JWTClaimsSet;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Service
@PropertySource({ "classpath:application.properties" })
public class TokenService {
	
	@Autowired
	UserBusiness utilisateurMetie;
	
	@Autowired
    private Environment env;

	private Role role;
	private List<String> roles;
	private String secretKey;
	private String userName;
	private static final int expireToken = 180; //seconde

	public static String tokenStart = "Bearer ";

	DirectEncrypter encrypter;
	byte[] secretKeyEncrypter;
	
	@PostConstruct
	public void start(){
		secretKey = env.getProperty("secret_key");
		secretKeyEncrypter = secretKey.getBytes();
	}
	
	public String createToken(UserDb utilisateur){
			role = utilisateur.getRoleUser();
			roles = new ArrayList<>();
			role.getRole().forEach(a -> {
				roles.add("ROLE_" + a);
			});
			
			JWTClaimsSet jWTClaimsSet = new JWTClaimsSet.Builder()
					.subject(utilisateur.getUserName())
					.issueTime(new Date())
					.expirationTime(generateExpirationDate(expireToken))
					.claim("role", utilisateur.getRoleUser().getId())
					.claim("roles", roles)
					.claim("created", new Date())
					.build();
		
		     Payload payload = new Payload(jWTClaimsSet.toJSONObject());
		     JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);
		     
		     try {
				encrypter = new DirectEncrypter(secretKeyEncrypter);
			} catch (KeyLengthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     JWEObject jweObject = new JWEObject(header, payload);
		     try {
				jweObject.encrypt(encrypter);
			} catch (JOSEException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     String token = jweObject.serialize();

		   return  tokenStart+token;	
		}
		

	  private Date generateExpirationDate(int expirationPeriod) {
	      return new Date(System.currentTimeMillis() + expirationPeriod * 1000);
	  }

}
