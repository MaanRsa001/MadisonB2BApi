package com.maan.Madison.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.Madison.utilityServiceImpl.ErrorList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {
	

	@JsonProperty("Message")
	private String message;
	
	@JsonProperty("Response")
	private Object response;
	
	@JsonProperty("Errors")
	private List<ErrorList> errors;

}
