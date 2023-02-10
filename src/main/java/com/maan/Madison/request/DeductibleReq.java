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
public class DeductibleReq {
	
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("SeatCapacity")
	private String seatCapacity;
	@JsonProperty("VehicleUsage")
	private String vehicleUsage;
	@JsonProperty("BodyId")
	private String bodyId;
}
