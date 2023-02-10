package com.maan.Madison.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuoteRes {
	
	@JsonProperty("QuoteInfo")
	private QuoteInfoRes quoteInfo;
	
	@JsonProperty("VehicleInfo")
	private List<VehicleInfoRes> vehicleInfo;
	
	@JsonProperty("CustomerInfo")
	private CustomerInfoRes customerInfo;
	
	@JsonProperty("Premium")
	private PremiumInfoRes premium;
	
	@JsonProperty("ReferalStatus")
	private String referalStatus;
	@JsonProperty("ReferalRemarks")
	private String referalRemarks;
	@JsonProperty("QuoteNo")
	private String quoteNo;
	@JsonProperty("ApplicationNo")
	private String applicationNo;
}
