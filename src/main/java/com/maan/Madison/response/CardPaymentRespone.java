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
public class CardPaymentRespone {

	@JsonProperty("Amount")
	private String amount;
	@JsonProperty("ReferenceNo")
	private String referenceNo;
	@JsonProperty("Currency")
	private String currency;;
	@JsonProperty("RecurringAmount")
	private String recurring_amount;
	@JsonProperty("InstallmentYN")
	private String installmentYN;
	@JsonProperty("RecurringFrequency")
	private String recurringFrequency;
	@JsonProperty("RecurringStartDate")
	private String recurringStartDate;
	@JsonProperty("NoOfInstallment")
	private String noOfInstallment;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("SurName")
	private String surName;
	@JsonProperty("Address")
	private String address;
	@JsonProperty("City")
	private String city;
	@JsonProperty("Country")
	private String country;
	@JsonProperty("PostalCode")
	private String postalCode;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("ProfileId")
	private String profileId;
	@JsonProperty("AccessKey")
	private String accessKey;
	@JsonProperty("SecretKey")
	private String SecretKey;
	@JsonProperty("Locale")
	private String locale;
	@JsonProperty("TransactionUuid")
	private String transactionUuid;
	@JsonProperty("Signature")
	private String signature;
	@JsonProperty("TransactionType")
	private String transactionType;
	@JsonProperty("Recurring_automatic_renew")
	private String recurring_automatic_renew;
	

}
