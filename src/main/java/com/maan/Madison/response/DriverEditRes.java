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
public class DriverEditRes {

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
