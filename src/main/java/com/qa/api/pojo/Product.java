package com.qa.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Make sure that Do NOT make any mistake while create a POJO class, IF we make any spelling mistake then code will NOT RUN

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	private Integer id;
	private String title;
	private double price;
	private String description;
	private String category;
	private String image;
	private Rating rating;
	
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Rating{
		private double rate;
		private int count;
	}

	
	

}
