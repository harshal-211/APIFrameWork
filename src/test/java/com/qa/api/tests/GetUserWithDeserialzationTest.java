package com.qa.api.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserWithDeserialzationTest extends BaseTest{
	
	@Test
	public void getAllUserTest() {
		
		
		Response response = restClient.get(BASE_URL_GOREST, "/public/v2/users", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertEquals(response.getStatusCode(), 200);
	    System.out.println(response);

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
}
}