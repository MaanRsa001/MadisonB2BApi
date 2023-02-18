package com.maan.Madison.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MotorIntegrationRequest {
	
	@JsonProperty("QuoteNo")
	private String quoteNo;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("ProductId")
	private String productId;
}
