package com.qa.product.api.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidator;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITestWithJsonPathValidator extends BaseTest {
	
	@Test
	public void getProductTest() {
		
		Response getResponse = restClient.get(BASE_URL_PRODUCT ,"/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(getResponse.getStatusCode(), 200);
		
		List<Number> price = JsonPathValidator.readList(getResponse, "$[?(@.price>50)].price");
		System.out.println(price );
		System.out.println("*****************************");		
		
		List<Number> rate = JsonPathValidator.readList(getResponse, "$[?(@.price>50)].rating.rate");
		System.out.println( rate);
		System.out.println("*****************************");
		
		List<Map<String,Object>> jewleryList = JsonPathValidator.readListofMap(getResponse, "$[?(@.category == 'jewelery')].['price','title']");
		System.out.println(jewleryList);
		System.out.println(jewleryList.size());
		
		for(Map<String,Object> product: jewleryList)
		{
			String Title = (String) product.get("title");
			Number PRICE = (Number) product.get("price");
			System.out.println("title" + Title);
			System.out.println("price" + PRICE);
			System.out.println("--------------------------");
		}
		System.out.println("*****************************");
		
		Double MaximumPrice = JsonPathValidator.read(getResponse, "max($[*].price)");
		System.out.println("Maximun price : " + MaximumPrice );
		
		
		
	}
	
	
	

}
