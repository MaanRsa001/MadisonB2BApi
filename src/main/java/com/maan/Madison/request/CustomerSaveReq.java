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
public class CustomerSaveReq {
	
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastname;
	@JsonProperty("MobileNo")
	private String mobileno;
	@JsonProperty("DateOfBirth")
	private String dateOfBirth;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("CustomerType")
	private String customerTyp;
	@JsonProperty("BrokerCode")
	private String BrokerCode;
	@JsonProperty("LoginId")
	private String loginId;

}
