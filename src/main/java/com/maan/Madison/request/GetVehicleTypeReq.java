package com.maan.Madison.request;

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
public class GetVehicleTypeReq {
	
	@JsonProperty("ApplicationNo")
	private String applicationNo;
	@JsonProperty("VehicleId")
	private String vehicleId;
	@JsonProperty("MakeId")
	private String makeId;
	@JsonProperty("ModelId")
	private String modelId;
	@JsonProperty("BranchCode")
	private String branchCode;
	

}
