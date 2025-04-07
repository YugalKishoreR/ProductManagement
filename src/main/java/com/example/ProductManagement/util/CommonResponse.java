package com.example.ProductManagement.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse {

	private String errorMessage;
	private String errorCode;
	private String errorStatus;
	private String message;

}
