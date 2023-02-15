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
public class VehicleEditRes {
	
	@JsonProperty("ApplicationNo")
	private String applicationNo;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("QuoteNo")
	private String quoteNo;
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
	@JsonProperty("DriverDateOfBirth")
	private String driverDateOfBirth;
	@JsonProperty("LicenseNo")
	private String licenseNo;
	@JsonProperty("PolicyType")
	private String policyType;
	@JsonProperty("CurrencyType")
	private String currencyType;
	@JsonProperty("PolicyStartDate")
	private String policyStartDate;
	@JsonProperty("PolicyEndDate")
	private String policyEndDate;
	@JsonProperty("ProductId")
	private String ProductId;
	@JsonProperty("BodyName")
	private String bodyName;
	@JsonProperty("MakeName")
	private String makeName;
	@JsonProperty("ModelName")
	private String modelName;
	@JsonProperty("VehicleUsageName")
	private String vehicleUsageName;
	
}
