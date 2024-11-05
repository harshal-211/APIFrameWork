package com.qa.product.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.client.RestClient;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITest extends BaseTest {
	
	
	// BaseURl = https://fakestoreapi.com
	@Test
	public void getAllProductTest() {
		Response getResponse = restClient.get(BASE_URL_PRODUCT ,"/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(getResponse.getStatusCode(), 200);
	}
	
	// Do all cases for all scenario

	
}