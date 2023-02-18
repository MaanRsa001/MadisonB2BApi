package com.maan.Madison.response;

import java.util.List;

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
public class PolicyIntegrationRes {
	
	@JsonProperty("PolicyNo")
	private String policyNo;
	@JsonProperty("Premium")
	private String Premium;
	@JsonProperty("DebitNo")
	private String debitNo;
	@JsonProperty("ReceiptNo")
	private String receiptNo;
	@JsonProperty("DocumentInfo")
	private List<PolicyDocumentRes> documentInfo;
	
}
