package com.qa.reqresp.api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ReqRespTest extends BaseTest{

	
	// this class is created to call 3ed party API
	
	@Test
	public void getReqResUserTest() {
		
		User user = new User(null, "Harshal", StringUtility.getRandomEmail(), "male", "active");
		
		Response response = restClient.get(BASE_URL_REQ_RES, "/api/users?page=2",null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 200);
}
}
