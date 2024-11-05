package com.qa.api.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

public class JsonUtils {


	private static ObjectMapper objectMapper = new ObjectMapper();
	
	// We are using <T> generic along with T(this T is for return type) to call targeted class and  of void we use <T> 
	public static <T>T deserialize(Response response, Class<T> targetClass){
		
		try {
			return objectMapper.readValue(response.getBody().asString(), targetClass);
		}
		
		catch (Exception e) {
			throw new RuntimeException("Deserialization is Failed....." + targetClass.getName());
		} 
	}
	
	
}
