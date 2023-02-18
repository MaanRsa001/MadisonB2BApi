package com.maan.Madison.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PremiumInfoRes {

	@JsonProperty("BasePremium")
	private String basePremium;
	@JsonProperty("ElectricalAccesPremium")
	private String electricalAccesPremium;
	@JsonProperty("NonElectricalAccesPremium")
	private String nonElectricalAccesPremium;
	@JsonProperty("PremiumBeforTax")
	private String premiumBeforTax;
	@JsonProperty("PolicyFees")
	private String policyFees;
	@JsonProperty("OverAllPremium")
	private String overAllPremium;
	@JsonProperty("Deductibles")
	private String deductibles;
	@JsonProperty("MinimumPremiumAdjust")
	private String minimumPremiumAdjust;
	@JsonProperty("BasePremiumRate")
	private String basePremiumRate;
	@JsonProperty("DeductiblesRate")
	private String deductiblesRate;
}
