package com.github.loginservice.models;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Document(collection = "role")
@Getter
@Setter
@ToString
public class Role {
	@Id 
	private String id;
	private String firstName;
	private String lastName;
	private List<String> role;
	private String userName;
	private Date creationDate;
	
	
}
