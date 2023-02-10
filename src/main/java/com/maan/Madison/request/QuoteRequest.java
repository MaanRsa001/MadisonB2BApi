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
public class QuoteRequest {
	
	@JsonProperty("PolicyReq")
	private PolicyInfoRequest policyInfo;
	
	@JsonProperty("ApplicationNo")
	private String applicationNo;
	
	@JsonProperty("QuoteNo")
	private String quoteNo;
	
	@JsonProperty("BrokerCode")
	private String brokerCode;
	
	@JsonProperty("LoginId")
	private String loginId;
	
	@JsonProperty("BranchCode")
	private String branchCode;
	
	@JsonProperty("UserType")
	private String userType;
	
	@JsonProperty("CustomerId")
	private String customerId;
	
}
