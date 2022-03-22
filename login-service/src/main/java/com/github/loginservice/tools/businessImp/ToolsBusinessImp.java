package com.github.loginservice.tools.businessImp;

import java.util.List;

import com.github.loginservice.tools.business.ToolsBusiness;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Service
public class ToolsBusinessImp implements ToolsBusiness {
	private Gson gson = new Gson();
	private JsonElement element;
	private JsonObject jsonObject ;
	private JsonArray jsonArray;
	
	@Override
	public String objectToParse(Object object) {
		element = gson.toJsonTree(object, new TypeToken<Object>() {
		}.getType());
		jsonObject = element.getAsJsonObject();
		return jsonObject.toString();
	}

	@Override
	public String arrayObjectToParse(List lObject) {
		element = gson.toJsonTree(lObject, new TypeToken<List<Object>>() {
		}.getType());
		jsonArray = element.getAsJsonArray();
		return jsonArray.toString();
	}

	@Override
	public Object parseToObject(String parse, Object object) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.create();
		Object resultat = gson.fromJson(parse, object.getClass());
		return resultat;
	}
}
