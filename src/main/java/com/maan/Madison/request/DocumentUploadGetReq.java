package com.maan.Madison.request;

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
public class DocumentUploadGetReq {
	
	@JsonProperty("QuoteNo")
	private String quoteNo;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("VtypeId")
	private String vtypeId;

}
