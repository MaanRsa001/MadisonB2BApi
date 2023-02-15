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
public class DocumentUploadReq {
	
	@JsonProperty("QuoteNo")
	private String quoteNo;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("VtypeId")
	private String vtypeId;
	@JsonProperty("DocumentTypeId")
	private String documentTypeId;
	@JsonProperty("Description")
	private String description;
	
}
