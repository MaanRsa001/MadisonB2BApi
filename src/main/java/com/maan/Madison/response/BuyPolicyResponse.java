package com.maan.Madison.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyPolicyResponse {
	
	@JsonProperty("QuoteNo")
	private String quoteNo;
	@JsonProperty("Product")
	private String product;
	@JsonProperty("CustomerName")
	private String customerName;
	@JsonProperty("MobileNo")
	private String mobileNo;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("Premium")
	private String Premium;
	@JsonProperty("BranchCode")
	private String branchCode;
	
}
