package com.qa.api.client;
import java.io.File;
import static org.hamcrest.Matchers.*;

import java.util.Base64;
import java.util.Map;
import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.FrameworkException;
import com.qa.api.manager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.expect;

public class RestClient{
	
	// defining response specifications
	
	private ResponseSpecification response200 = expect().statusCode(200);
	private ResponseSpecification response201 = expect().statusCode(201);
	private ResponseSpecification response200or404 = expect().statusCode(anyOf(equalTo(200),equalTo(404)));  //	//This will be used when we have to check status code after deletetion of user (by using GET call) import ===> {static org.hamcrest.Matchers.*;}
	private ResponseSpecification response201or200 = expect().statusCode(anyOf(equalTo(201),equalTo(200)));
	private ResponseSpecification response204 = expect().statusCode(204);
	private ResponseSpecification response400 = expect().statusCode(400);
	private ResponseSpecification response401 = expect().statusCode(401);
	private ResponseSpecification response404 = expect().statusCode(404);
	private ResponseSpecification response422 = expect().statusCode(422);
	private ResponseSpecification response500 = expect().statusCode(500);
	private ResponseSpecification response503 = expect().statusCode(503);
	
	
	
	
	// ************************************************************************************************************************
	
    // this is for getting baseURL from ConfigManager which is loaded from config file
	//private String baseUrl = ConfigManager.get("baseUrl");  ----> No use of this line as we are passing baseURL in method it self
	
	
	private RequestSpecification setupRequest(String baseUrl, AuthType authType, ContentType contentType) {
			
		RequestSpecification request = RestAssured.given().log().all()
		                                               .baseUri(baseUrl)
		                                                 .contentType(contentType)
		                                                        .accept(contentType); // this contentType will return RequestSpecification
		
		//here we are using switch case to switch b/w different token to hit the API which are Enums
		switch(authType){
		
		case BEARER_TOKEN:
			request.header("Authorization", "Bearer "+ ConfigManager.get("bearerToken"));  // value of token will be taken from config.prop file
			break;
		case CONTACTS_BEARER_TOKEN:
			request.header("Authorization", "Bearer "+ ConfigManager.get("contacts_bearer_Token"));  // value of token will be taken from config.prop file
			break;
		case OAUTH2:
			request.header("Authorization", "Bearer "+ generateOAuth2Token());  // VALUE for token is coming from generateOAuth method
			break;	
		case BASIC_AUTH:
			request.header("Authorization", "Basic "+ generateBasicAuthToken());
			break;			
		case NO_AUTH:
			System.out.println("No Auth Required....");
			break;			
		case API_KEY:
			request.header("x-api-key", ConfigManager.get("apiKey"));    // getting value from config file
			break;
			
			default:
			System.out.println("Thios Auth is NOT supported... please pass the right AuthType...");
			throw new FrameworkException("NO AUTH SUPPORTED");
		}
		  
		return request;	
	}
		
		
		//this method is created to generate OAuth2 token and after generation get token it get loaded into above para for the value to Oauth2-token
		private String generateOAuth2Token() {
			return RestAssured.given()
					           .formParam("client_id", ConfigManager.get("clientId"))
					           .formParam("client_secret", ConfigManager.get("clientSecret"))
					           .formParam("grant_type", ConfigManager.get("grantType"))
					           .post(ConfigManager.get("tokenURL"))
					           .then()
					           .extract()
					           .path("access_token");
		}
		
		/**
		 * Method is used to generate base64 encoded String
		 * @return
		 */
		private String generateBasicAuthToken() {
				String credential = ConfigManager.get("basicUsername")+ ":" + ConfigManager.get("basicPassword");  //admin:admin
				return Base64.getEncoder().encodeToString(credential.getBytes());
				
		}
		
		
		private void applyParams(RequestSpecification request,Map<String, String> queryParams,  Map<String, String> pathParams) {
			if(queryParams != null)
			{
				request.queryParams(queryParams);
			}
			if(pathParams != null)
			{
				request.pathParams(pathParams);
			}
		}
		
		private RequestSpecification setUpAuthAndContentType(String baseUrl,AuthType authType, ContentType contentType){
			return setupRequest(baseUrl, authType, contentType);
		}
		
		//******************************************* CURD Method ************************************************************
		
		/**
		 * This method is used to call GET API (all those parameters are used in it)
		 * @param endPoint
		 * @param queryParam
		 * @param pathParam
		 * @param authType
		 * @param contentType
		 * @return it returns the GET method response
		 * We are passing BaseURL in same method, but we are NOT appending  it with end point because for some case end-point or base URL can be changed
		 */
		public Response get(String baseUrl, String endPoint, 
				            Map<String, String> queryParam,  Map<String, String> pathParam, AuthType authType, ContentType contentType) {
			
			// now we are calling setup_request method internally as it is private method to get authType and ContentType from there
			
			RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);
			applyParams(request, queryParam, pathParam);			
			Response response =  request.get(endPoint).then().spec(response200or404).extract().response();
			response.prettyPrint();
			return response;
		}
		
		
		
		// For post call, we have to pass the body, so here we are passing T(means we can pass any type of genetics) in the method arg )
		
		/**
		 * This method is used to call POST APIs & it returns the POST method response
		 * @param <T>
		 * @param endPoint
		 * @param body
		 * @param queryParam
		 * @param pathParam
		 * @param authType
		 * @param contentType
		 * @return
		 */
		public <T>Response post(String baseUrl,String endPoint, T body, Map<String, String> queryParam, 
	                            Map<String, String> pathParam, AuthType authType, ContentType contentType) {
			
			RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);
			applyParams(request, queryParam, pathParam);
			Response response =  request.body(body).post(endPoint).then().spec(response201or200).extract().response();
			response.prettyPrint();	
			return response;
			
	}

      // this method is for create user but data is coming from jsonFile, so here in body we pass file and remove <T> generic 
		/**
		 * This method is used to call POST APIs for which data is coming from JSON File & it returns the POST method response
		 * @param endPoint
		 * @param file
		 * @param queryParam
		 * @param pathParam
		 * @param authType
		 * @param contentType
		 * @return
		 */
		public Response postJsonFile(String baseUrl,String endPoint, File file, Map<String, String> queryParam, 
                Map<String, String> pathParam, AuthType authType, ContentType contentType) {

			RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);
             applyParams(request, queryParam, pathParam);
             Response response =  request.body(file).post(endPoint).then().spec(response201).extract().response();
             response.prettyPrint();	
             return response;

             }
		/**
		 * Method for PUT call, in return we get response
		 * @param <T>
		 * @param endPoint
		 * @param body
		 * @param queryParam
		 * @param pathParam
		 * @param authType
		 * @param contentType
		 * @return
		 */
		public <T>Response put(String baseUrl,String endPoint, T body, Map<String, String> queryParam, 
                Map<String, String> pathParam, AuthType authType, ContentType contentType) {

		RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);
        applyParams(request, queryParam, pathParam); 
        Response response =  request.body(body).put(endPoint).then().spec(response200).extract().response();
        response.prettyPrint();	
        return response;

            }
		
		/**
		 * This method is used to call PATCH APIs & it returns the PATCH method response
		 * @param <T>
		 * @param endPoint
		 * @param body
		 * @param queryParam
		 * @param pathParam
		 * @param authType
		 * @param contentType
		 * @return
		 */
		public <T>Response patch(String baseUrl,String endPoint, T body, Map<String, String> queryParam, 
                Map<String, String> pathParam, AuthType authType, ContentType contentType) {

			RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);
        applyParams(request, queryParam, pathParam); 
        Response response =  request.body(body).patch(endPoint).then().spec(response200).extract().response();
        response.prettyPrint();	
        return response;
		
		
		}
		/**
		 * This method is used to call DELETE APIs & it returns the DELETE method response
		 * @param <T>
		 * @param endPoint
		 * @param queryParam
		 * @param pathParam
		 * @param authType
		 * @param contentType
		 * @return
		 */
		public <T>Response delete(String baseUrl,String endPoint, Map<String, String> queryParam, 
                Map<String, String> pathParam, AuthType authType, ContentType contentType) {

			RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);
        applyParams(request, queryParam, pathParam); 
        Response response =  request.delete(endPoint).then().spec(response204).extract().response();
        response.prettyPrint();	
        return response;
		
		}
		
		
		
		
		
		
}














