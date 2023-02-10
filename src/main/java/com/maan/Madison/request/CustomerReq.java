package com.maan.Madison.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CustomerReq {
	
	@JsonProperty("BrokerCode")
	private String brokerCode;
	@JsonProperty("CustomerType")
	private String customerType;

}
