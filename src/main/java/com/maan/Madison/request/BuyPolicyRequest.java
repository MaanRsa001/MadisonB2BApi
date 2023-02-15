package com.maan.Madison.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.Madison.response.BuyPolicyCustomerReq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BuyPolicyRequest {
	
	@JsonProperty("QuoteNo")
	private String quoteNo;
	@JsonProperty("ApplicationNo")
	private String applicationNo;
	@JsonProperty("PolicyStartDate")
	private String policyStartDate;
	@JsonProperty("PolicyEndDate")
	private String policyEndDate;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("ReferalQuoteYn")
	private String referalQuoteYn;
	@JsonProperty("ReferalRemarks")
	private String referalRemarks;
	@JsonProperty("EmailQuoteYn")
	private String emailQuoteYn;
	@JsonProperty("InstallmentYn")
	private String installmentYn;
	@JsonProperty("GeneratePolicyYn")
	private String generatePolicyYn;
	@JsonProperty("CustomerInfo")
	private BuyPolicyCustomerReq customer;

}
