package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	
	
	//Properties{which is part of collection} is import from java.util to read the property from config file
	public static Properties properties = new Properties();
	
	static {
		
	//This is called reflection in java, means at runtime if you want to access the properties from any class then you can access that
	// InputStream --> imported from java.io package	
	// in getResourceAsStream --> we have to give the path of file from where we want to access the properties
	
		try(InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config/config.properties"))
		{
			// if we get properly input value then load the properties with whatever value of input 
			    if( input != null) 
			    {
			    	properties.load(input);
			    }
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	//creating static method "get" to get the value of all keys(whenever we call from properties file {where we store value in form of Key = value}
			public static String get(String key) {
				return properties.getProperty(key);
			}
	
			public static void set(String key, String value) {
				properties.setProperty(key, value);
			}
	

}
