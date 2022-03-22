package com.github.loginservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Document(collection = "SettingAdmin")
@Getter
@Setter
@ToString
public class SettingAdmin {
	
	@Id
	private String id;
	private int numberAttempt, numberAttemptDefault;
	private long times, timesDefault;
	private int defaultDateExpiryPasswordYears, defaultDateExpiryPasswordMonth, defaultDateExpiryPasswordDay,
	            datePasswordExpirationYears, datePasswordExpirationMonth, datePasswordExpirationDay;
}
