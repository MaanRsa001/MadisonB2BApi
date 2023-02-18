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
public class PolicyDocumentRes {

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
	@JsonProperty("CertificateNo")
	private String certificateNo;
}
