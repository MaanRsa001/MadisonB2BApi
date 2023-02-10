package com.maan.Madison.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoRes {
	
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
	@JsonProperty("Address")
	private String address;
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
	@JsonProperty("PassportNo")
	private String passportNo;
	@JsonProperty("CustomerType")
	private String customerTyp;
	@JsonProperty("CompanyRegNo")
	private String companyRegNo;
	@JsonProperty("BrokerCode")
	private String brokerCode;
	@JsonProperty("CustomerId")
	private String customerId;

}
