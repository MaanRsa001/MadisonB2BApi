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
public class VehicleInfoRes {

	@JsonProperty("VehicleUsage")
	private String vehicleUsage;
	@JsonProperty("Make")
	private String make;
	@JsonProperty("Model")
	private String model;
	@JsonProperty("BodyType")
	private String bodyType;
	@JsonProperty("VehicleValue")
	private String vehicleValue;
	@JsonProperty("Premium")
	private String premium;
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
	
}
