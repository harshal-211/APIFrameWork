package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PatchUserTest extends BaseTest {
	
	@Test
	public void updateUserNameAndGenderTest() {
		
		User user = User.builder()
		.email(StringUtility.getRandomEmail())
		.name("Balvant Raistar")
		.gender("male")
		.status("active")
		.build();
		
		//User created
		Response postResponse = restClient.post(BASE_URL_GOREST, "/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(postResponse.getStatusCode(), 201);
		String UserId = postResponse.jsonPath().getString("id");
		System.out.println("UserID ===>"+ UserId);
		System.out.println("=======================================");
		
		//Get newly Created user
		Response getResponse = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+UserId , null, null,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getResponse.statusCode(), 200);
		Assert.assertEquals(getResponse.jsonPath().getString("id"), UserId);		
		Assert.assertEquals(getResponse.jsonPath().getString("name"), user.getName());
		System.out.println("========================================");
		
		//Update User Name
		user.setName("tulsi cyrup");
		user.setGender("female");
		Response updatedResposne = restClient.patch(BASE_URL_GOREST, "/public/v2/users/"+UserId, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(updatedResposne.getStatusCode(), 200);
		Assert.assertEquals(updatedResposne.jsonPath().getString("id"), UserId);
		Assert.assertEquals(updatedResposne.jsonPath().getString("name"), user.getName());
		Assert.assertEquals(updatedResposne.jsonPath().getString("gender"), user.getGender());
		
		
		
		
		
		
		
		
		
		
	}

}
