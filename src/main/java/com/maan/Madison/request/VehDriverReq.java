package com.maan.Madison.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VehDriverReq {
	

	@JsonProperty("ApplicationNo")
	private String applicationNo;
	@JsonProperty("VehicleId")
	private String vehicleId;
	@JsonProperty("DriverId")
	private String driverId;
	@JsonProperty("DriverDob")
	private String DriverDob;
	@JsonProperty("Claimyn")
	private String claimyn;
	@JsonProperty("PolicyNo")
	private String policyNo;
	@JsonProperty("PrePolicyExpDate")
	private String prePolicyExpDate;
	@JsonProperty("NoOfClaimBonus")
	private String noOfClaimBonus;
	@JsonProperty("ClaimAmt")
	private String claimAmt;
	@JsonProperty("InsCompany")
	private String insCompany;
	@JsonProperty("IsClaimDtl")
	private String isClaimDtl;
	@JsonProperty("OwnnerdriverYn")
	private String ownnerdriverYn;
}


