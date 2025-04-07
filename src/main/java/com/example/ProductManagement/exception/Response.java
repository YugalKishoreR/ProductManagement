package com.example.ProductManagement.exception;

import lombok.Data;

@Data
public class Response<T> {

	private String status;
	private String message;
	private T data;

	public Response(String message, String status, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public Response(String message) {

		this.message = message;

	}

}
