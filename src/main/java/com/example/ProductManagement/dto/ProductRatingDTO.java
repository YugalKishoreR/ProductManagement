package com.example.ProductManagement.dto;

import lombok.Data;

@Data
public class ProductRatingDTO{

	private int id;
    private int pid;
    private double rating;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
    
    

}
