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
public class VehicleInfoRequest {
	
	@JsonProperty("VehicleId")
	private String vehicleId;
	@JsonProperty("MakeId")
	private String makeId;
	@JsonProperty("ModelId")
	private String modelId;
	@JsonProperty("BodyTypeId")
	private String bodyTypeId;
	@JsonProperty("SumInsured")
	private String sumInsured;
	@JsonProperty("ManufacureYear")
	private String manufacureYear;
	@JsonProperty("SeatingCapacity")
	private String seatingCapacity;
	@JsonProperty("ChassisNo")
	private String chassisNo;
	@JsonProperty("EngineNo")
	private String engineNo;
	@JsonProperty("PlateNo")
	private String plateNo;
	@JsonProperty("PlateChar")
	private String plateChar;
	@JsonProperty("PreviousClaimYN")
	private String previousClaimYN;
	@JsonProperty("NoOfClaims")
	private String noOfClaims;
	@JsonProperty("BankOfFinance")
	private String bankOfFinance;
	@JsonProperty("VehicleColor")
	private String vehicleColor;
	@JsonProperty("RegistrationNo")
	private String registrationNo;
	@JsonProperty("EngineCapacity")
	private String engineCapacity;
	@JsonProperty("ExcessLimit")
	private String excessLimit;
	@JsonProperty("VehicleUsage")
	private String vehicleUsage;
	@JsonProperty("LeasedYn")
	private String leasedYn;
	@JsonProperty("ElectricalAccesAmt")
	private String electricalAccesAmt;
	@JsonProperty("NonElectricalAccesAmt")
	private String NonElectricalAccesAmt;
	
}
