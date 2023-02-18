package com.maan.Madison.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadReq implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	@JsonProperty("FileName")
	private String fileName;
	@JsonProperty("Base64File")
	private String base64File;
	
}
