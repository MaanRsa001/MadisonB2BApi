package com.maan.Madison.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveVehicleRes {
	
	@JsonProperty("ApplicationNo")
	private String applicationNo;
	@JsonProperty("VehicleId")
	private String vehicleId;
}
