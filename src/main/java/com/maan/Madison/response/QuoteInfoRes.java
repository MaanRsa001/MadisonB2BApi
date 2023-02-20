package com.maan.Madison.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteInfoRes {
	
	@JsonProperty("QuoteNo")
	private String quoteNo;
	@JsonProperty("CustomerName")
	private String customerName;
	@JsonProperty("PolicyType")
	private String policyType;
	@JsonProperty("QuoteDate")
	private String quoteDate;
	@JsonProperty("ProductName")
	private String productName;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("ProductId")
	private String productId;

}
