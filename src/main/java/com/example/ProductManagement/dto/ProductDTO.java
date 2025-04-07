package com.example.ProductManagement.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductDTO {

	private int id;
    private String productId;
    private String productName;
    private String brand;
    private String category;
    private int price;
    private List<ProductRatingDTO> ratings;
    private double averageRating;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public List<ProductRatingDTO> getRatings() {
		return ratings;
	}
	public void setRatings(List<ProductRatingDTO> ratings) {
		this.ratings = ratings;
	}
	public double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	
}
