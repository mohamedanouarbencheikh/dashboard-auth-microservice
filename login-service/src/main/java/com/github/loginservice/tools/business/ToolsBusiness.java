package com.github.loginservice.tools.business;

import java.util.List;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
public interface ToolsBusiness {

	String objectToParse(Object object);
	String arrayObjectToParse(List lObject);
	Object parseToObject(String parse,Object object);
}
