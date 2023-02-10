package com.maan.Madison.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayeeRequest {

	@JsonProperty("partyIdType")
	private String partyIdType;
	@JsonProperty("partyId")
	private String partyId;
	
	
}
