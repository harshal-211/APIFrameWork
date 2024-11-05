package com.qa.api.utils;

public class StringUtility {

	public static String getRandomEmail() {
		
		/*
		 * Reason to use "API_Automation" at starting because if in DB there is so many
		 * entry and now I want to delete all those newly created user then simple I can
		 * use query like select * from table_name where email like '%API_Automation%';
		 */
		return "API_Automation"+System.currentTimeMillis()+"@openMart.com" ;
		
	}
}


