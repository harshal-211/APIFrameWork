package com.qa.api.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest {
	
	@Test
	public void createUsertest() {
		
		User user = new User(null , "Harshal", StringUtility.getRandomEmail(), "male", "active"); // We Add null because we add ID in Users lombok class
		
		Response response = restClient.post(BASE_URL_GOREST, "/public/v2/users",user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
		String userId = response.jsonPath().getString("id");
		System.out.println("UserID -----> "+ userId);
		
		//getting newly created----> GET call
		Response getResponse = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getResponse.getStatusCode(), 200);
		Assert.assertEquals(getResponse.jsonPath().getString("id"), userId);	
		Assert.assertEquals(getResponse.jsonPath().getString("name"), user.getName());
	    Assert.assertEquals(getResponse.jsonPath().getString("status"), user.getStatus());
	    Assert.assertEquals(getResponse.jsonPath().getString("email"), user.getEmail());
	
	    // above test is for create new user and get the user
	
	}
	
	// create new User --> get the same user --> update the same user --> get the same user
	
	@Test
	public void createUserWithBuildertest() {
		
		User user2 = User.builder()
		     .name("bala")
		     .email(StringUtility.getRandomEmail())
		     .status("active")
		     .gender("male")
		     .build();
		
		//CREATE New User
		Response response = restClient.post(BASE_URL_GOREST, "/public/v2/users",user2, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		String user2Id = response.jsonPath().getString("id");     // in user2Id ==> only id is storing that's it
		Assert.assertEquals(response.getStatusCode(), 201);
		System.out.println("User2Id ====>"+ user2Id);
		
		//GET the Newly Added user
		Response getResponse2  = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+user2Id, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getResponse2.getStatusCode(), 200);
		Assert.assertEquals(getResponse2.jsonPath().getString("id"), user2Id);
		Assert.assertEquals(getResponse2.jsonPath().getString("name"), user2.getName());
		Assert.assertEquals(getResponse2.jsonPath().getString("status"), user2.getStatus());
		//getResponse2.prettyPrint();
	
		//UPDATE the Status of user and changing the email ID
		user2.setStatus("inactive");
		user2.setEmail(StringUtility.getRandomEmail());	
		Response updatedResponse = restClient.put(BASE_URL_GOREST, "/public/v2/users/"+user2Id, user2, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
 		Assert.assertEquals(updatedResponse.getStatusCode(), 200);
 		Assert.assertEquals(updatedResponse.jsonPath().getString("id"), user2Id);
 		Assert.assertEquals(updatedResponse.jsonPath().getString("status"), user2.getStatus());
 		Assert.assertEquals(updatedResponse.jsonPath().getString("email"), user2.getEmail());
	
	}
	
	/*
	 * @Test public void createUserWithFileDataTest() {
	 * 
	 * //passing data from file, make sure that mention dot "." at the starting of
	 * path File jsonFile = new File("./src/test/resources/jsons/userData.json");
	 * Response response = restClient.post("/public/v2/users",jsonFile, null, null,
	 * AuthType.BEARER_TOKEN, ContentType.JSON);
	 * Assert.assertEquals(response.getStatusCode(), 201);
	 * 
	 * }
	 */
}
