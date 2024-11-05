package com.qa.api.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import io.restassured.response.Response;

public class JsonPathValidator{

	private static String getJsonResponseAsString(Response response) {
		return response.getBody().asString();
		
	}
	// It depends on jsonPath -- which kind of List we are getting, Thats why we use <T> ---- This is only for single Attribute List
	public static <T> List<T> readList(Response response, String jsonPath){
		String jsonResponse = getJsonResponseAsString(response);
        ReadContext ctx = JsonPath.parse(jsonResponse);
        return ctx.read(jsonPath);
	
	}

	public static <T>T read(Response response, String jsonPath){
		String jsonResponse = getJsonResponseAsString(response);
        ReadContext ctx = JsonPath.parse(jsonResponse);
        return ctx.read(jsonPath);
	
	}
	
	//It depends on jsonPath -- which kind of List we are getting, Thats why we use <T> ---- This is only for MAP Attribute List
	public static <T> List<Map<String, T>> readListofMap(Response response, String jsonPath){
		String jsonResponse = getJsonResponseAsString(response);
        ReadContext ctx = JsonPath.parse(jsonResponse);
        return ctx.read(jsonPath);
	
	}
	
	
	
	
	
	
}
