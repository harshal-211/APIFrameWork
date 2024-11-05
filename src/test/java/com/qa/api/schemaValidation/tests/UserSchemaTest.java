package com.qa.api.schemaValidation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class UserSchemaTest extends BaseTest{
	
	@Test
	public void userSchemaTest(){
		
//		RestAssured.given()
//        .baseUri("https://gorest.co.in")  // we can add header as well
//        .when()
//        .get("/public/v2/users/7502414")
//        .then()
//        .assertThat()
//        .statusCode(200)
//        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user-schema.json")); 
		
		User user = User.builder()
		.name("paaji")
		.email(StringUtility.getRandomEmail())
		.status("active")
		.gender("male")
		.build();
		
		Response postResponse =  restClient.post(BASE_URL_GOREST, "/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		String UserID = postResponse.jsonPath().getString("id");
		System.out.println("User_ID ---->"+ UserID);
		
		Response response =  restClient.get(BASE_URL_GOREST, "/public/v2/users/"+UserID, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(SchemaValidator.validateSchema(response, "schema/user-schema.json"), true);

		
		// Assignment --> Do for all Gorest class & other test classes (SchemaValidation)
		
	}
	

}
