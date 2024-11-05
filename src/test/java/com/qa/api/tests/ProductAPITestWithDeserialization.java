package com.qa.api.tests;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.utils.JsonUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITestWithDeserialization extends BaseTest {
	
	@Test	
	public void getAllProductTest() {
		
        Response response = restClient.get(BASE_URL_PRODUCT, "/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 200);
        
        Product[] product = JsonUtils.deserialize(response, Product[].class);
        System.out.println(Arrays.toString(product));
		
	    
	    for(Product P: product)
	    {
	    	System.out.println("ID :"+P.getId());
	    	System.out.println("Title :"+ P.getTitle());
	    	System.out.println("Price :"+ P.getTitle());
	    	System.out.println("Rate :"+ P.getRating().getRate());
	    	System.out.println("Count :" + P.getRating().getCount());
	    	
	    	System.out.println("-----------------");
	    }
		
	}

}
