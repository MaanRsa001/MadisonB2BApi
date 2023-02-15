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
	
	@JsonProperty("RedirectUrl")
	private String redirectUrl;
	@JsonProperty("QuoteNo")
	private String quoteNo;
	@JsonProperty("ApplicationNo")
	private String applicationNo;
	@JsonProperty("ProductId")
	private String productId;
}
