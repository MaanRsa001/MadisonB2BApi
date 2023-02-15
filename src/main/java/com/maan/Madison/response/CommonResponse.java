package com.maan.Madison.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CommonResponse {
	

	@JsonProperty("Message")
	private String message;
	
	@JsonProperty("Response")
	private Object response;
	
	@JsonProperty("Errors")
	private Object errors;

}
