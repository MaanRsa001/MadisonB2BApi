package com.maan.Madison.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PaymentRequest {
	
	@JsonProperty("financialTransactionId")
	private String financialTransactionId;
	@JsonProperty("amount")
	private String amount;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("externalId")
	private String externalId;
	@JsonProperty("payer")
	private PayeeRequest payer;
	@JsonProperty("payerMessage")
	private String payerMessage;
	@JsonProperty("payeeNote")
	private String payeeNote;
	@JsonProperty("status")
	private String status;
	@JsonProperty("reason")
	private String reason;

}