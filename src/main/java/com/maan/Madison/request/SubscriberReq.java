package com.maan.Madison.request;

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
public class SubscriberReq {
	
	@JsonProperty("country")
	private String country;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("msisdn")
	private Integer msisdn;

}