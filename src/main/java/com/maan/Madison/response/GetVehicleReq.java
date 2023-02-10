package com.maan.Madison.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetVehicleReq {
	
	
	@JsonProperty("VehicleValue")
	private String vehicleValue;
	@JsonProperty("ApplicationNo")
	private String applicationNo;
	@JsonProperty("QuoteNo")
	private String quoteNo;
	@JsonProperty("VehicleId")
	private String vehicleId;
	@JsonProperty("MakeId")
	private String makeId;
	@JsonProperty("ModelId")
	private String modelId;
	@JsonProperty("BodyId")
	private String bodyId;
	@JsonProperty("VehicleUseageId")
	private String vehicleUseageId;
	@JsonProperty("PolicyType")
	private String policyType;
	@JsonProperty("PolicyStartDate")
	private String PolicyStartDate;
	@JsonProperty("PolicyEndDate")
	private String PolicyEndate;
	@JsonProperty("CurrencyType")
	private String currencyType;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("ProductId")
	private String productId;
}
