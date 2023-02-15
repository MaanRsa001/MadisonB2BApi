package com.maan.Madison.utilityServiceImpl;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorList{

	@JsonProperty("Code")
    private String code;
	@JsonProperty("Field")
    private String field;
	@JsonProperty("Message")
    private String message;
	
}
