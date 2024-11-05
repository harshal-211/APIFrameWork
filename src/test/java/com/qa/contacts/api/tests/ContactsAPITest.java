package com.qa.contacts.api.tests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsCredentials;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactsAPITest extends BaseTest {
	
	private String TokenID; 
	
	//CASE -  New Token is generating & that token is different from the token which is mention in config file 
	//        now we are generating the TokenID & setting the value of it on RUN-Time
	@BeforeMethod
	public void generateToken(){
		
		ContactsCredentials cred = ContactsCredentials.builder()
		                   .email("harshalgoswami@yopmail.com")
		                   .password("kamachisuperpro")
		                   .build();
		
		Response response =  restClient.post(BASE_URL_CONTACTS, "/users/login", cred, null, null, AuthType.NO_AUTH, ContentType.JSON);
		TokenID = response.jsonPath().getString("token");
		System.out.println("TokenId -----> "+TokenID);
		ConfigManager.set("contacts_bearer_Token", TokenID); 
		// KEY = contacts_bearer_token(which is mention in RestClient class) & the VALUE = TokenID (which we are generating) ==> And after this we are setting Key & value here
	}

	
	@Test
	public void getAllContacts() {
		Response getResponse = restClient.get(BASE_URL_CONTACTS, "/contacts", null, null, AuthType.CONTACTS_BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getResponse.getStatusCode(), 200);
		
		
	}
	
	

}
