package com.maan.Madison.response;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetVehicleTypeRes {
	
	@JsonProperty("VehicleUsage")
	private String vehicleUsage;
	@JsonProperty("Make")
	private String make;
	@JsonProperty("Model")
	private String model;
	@JsonProperty("BodyType")
	private String bodyType;
	@JsonProperty("MakeId")
	private String makeId;
	@JsonProperty("ModelId")
	private String modelId;
	@JsonProperty("BodyId")
	private String bodyId;
	@JsonProperty("VehicleUseageId")
	private String vehicleUseageId;
	

}
