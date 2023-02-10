package com.maan.Madison.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PolicyInfoRequest {
	
	@JsonProperty("PolicyType")
	private String policyType;
	@JsonProperty("PolicyStartState")
	private String policyStartState;
	@JsonProperty("PolicyEndDate")
	private String policyEndDate;
	@JsonProperty("CurrencyType")
	private String currencyType;
	
}
