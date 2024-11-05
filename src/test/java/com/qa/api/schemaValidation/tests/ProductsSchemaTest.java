package com.qa.api.schemaValidation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.SchemaValidator;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class ProductsSchemaTest extends BaseTest{
	
	@Test
	public void productSchemaTest() {
		
		//make sure that in schema file type should be correctly defined for as per contract, if we do something wrong, then case get fails
		
//		RestAssured.given()
//		             .baseUri("https://fakestoreapi.com")
//		             .when()
//		             .get("/products")
//		             .then()
//		             .assertThat()
//		             .statusCode(200)
//		             .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/product-schema.json")); 
//		
		Response response =   restClient.get(BASE_URL_PRODUCT, "/products", null, null, AuthType.NO_AUTH, ContentType.ANY);
		
		Assert.assertEquals(SchemaValidator.validateSchema(response, "schema/product-schema.json"), true); 	
		
	
	
	}
	

}
