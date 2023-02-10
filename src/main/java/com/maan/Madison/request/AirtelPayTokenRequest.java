package com.maan.Madison.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirtelPayTokenRequest {
	
	@JsonProperty("client_id")
	private String client_id;
	@JsonProperty("client_secret")
	private String client_secret;
	@JsonProperty("grant_type")
	private String grant_type;

}