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
public class AirtelPaymentRequest {
	
	@JsonProperty("reference")
	private String reference;
	@JsonProperty("subscriber")
	private SubscriberReq subscriber;
	@JsonProperty("transaction")
	private TransactionReq transaction;

}
