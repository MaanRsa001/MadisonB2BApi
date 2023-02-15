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
public class BuyPolicyCustomerReq {
	
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastname;
	@JsonProperty("DateOfBirth")
	private String dateOfBirth;
	@JsonProperty("Gender")
	private String gender;
	@JsonProperty("Occupation")
	private String occupation;
	@JsonProperty("Address1")
	private String address1;
	@JsonProperty("Address2")
	private String address2;
	@JsonProperty("City")
	private String city;
	@JsonProperty("PoBox")
	private String poBox;
	@JsonProperty("MobileNo")
	private String mobile;
	@JsonProperty("AlternatMobileNo")
	private String alternatMobileNo;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("Nrc")
	private String nrc;
	@JsonProperty("CustomerType")
	private String customerTyp;
	@JsonProperty("CompanyRegNo")
	private String companyRegNo;
	@JsonProperty("PassportNo")
	private String passportNo;
	
}
