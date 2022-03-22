package com.github.loginservice.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Document(collection = "user")
@Getter
@Setter
@ToString
public class UserDb {
	@Id 
	private String id;
	private String company;
	private String userName;
	private String email;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String country;
	private int postalCode;
	private String aboutMe;

	private String password;
	private Date creationDate;

	@DBRef
	private Role roleUser;

}
