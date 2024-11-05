package com.qa.api.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserTest extends BaseTest{
	
    
	@Test
	public void get_All_UserTest() {
		
		Map<String, String> queryParam = new HashMap<String, String>(); // Using this to pass queryParameter and PathParameters
		queryParam.put("name", "Dandapaani");     //this is my test data which can be write in test case class as well "Data should NOT be hardcoded in restClient"
		queryParam.put("status", "active");
		Response response = restClient.get(BASE_URL_GOREST, "/public/v2/users", queryParam, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertEquals(response.getStatusCode(), 200);
	    System.out.println(response);
		
	}
	
	/*
	 * @Test public void getSingleUserTest() {
	 * 
	 * // No queryParam is used here and we are fetching specific user by using ID
	 * Response response = restClient.get(BASE_URL_GOREST, "/public/v2/users/7472257", null, null,
	 * AuthType.BEARER_TOKEN, ContentType.JSON);
	 * 
	 * Assert.assertEquals(response.getStatusCode(), 200);
	 * System.out.println(response);
	 * 
	 * }
	 */
	
	
//	@Test
//	public void getSpecificUsertest() {
//		
//		Response getResponse = restClient.get(BASE_URL_GOREST, "/public/v2/users/7480127", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
//		Assert.assertEquals(getResponse.getStatusCode(), 200);
//		Assert.assertEquals(getResponse.jsonPath().getString("email"), "API_Automation1729428557221@openMart.com");
//		
//	}
	
	
	
	
	

}
