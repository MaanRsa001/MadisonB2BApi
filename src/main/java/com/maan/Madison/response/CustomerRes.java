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
public class CustomerRes {
	
	@JsonProperty("CustomerName")
	private String customerName;
	@JsonProperty("ContactNo")
	private String ContactNo;
	@JsonProperty("EmailId")
	private String emailId;
	@JsonProperty("Nrc")
	private String nrc;
	@JsonProperty("PassportNo")
	private String passportNo;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("BrokerCode")
	private String brokerCode;
}
