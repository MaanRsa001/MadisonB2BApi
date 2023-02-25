package com.maan.Madison.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RejectListRes {

	@JsonProperty("QuoteNo")
	private String quoteNo;
	
	@JsonProperty("LapsedRemarks")
	private String lapsedRemarks;
	
	@JsonProperty("LapsedDate")
	private String lapsedDate;
	
	@JsonProperty("ApplicationNo")
	private String applicationNo;
	
	@JsonProperty("Quotationdate")
	private String quotationdate;
	
	@JsonProperty("CustomerId")
	private String customerId;
	
	@JsonProperty("EffectiveDate")
	private String effectiveDate;
	
	@JsonProperty("CustName")
	private String custName;
	
	@JsonProperty("LoginId")
	private String loginId;
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("CompanyName")
	private String companyName;
	
	@JsonProperty("Rejected")
	private String rejected;
	
}
