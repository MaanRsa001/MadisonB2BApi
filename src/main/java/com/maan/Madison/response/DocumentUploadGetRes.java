package com.maan.Madison.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.Madison.request.DocumentUploadGetReq;

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
public class DocumentUploadGetRes {
	
	@JsonProperty("EngineNo")
	private String engineNo;
	@JsonProperty("ChassisNo")
	private String chassisNo;
	@JsonProperty("RegistrationNo")
	private String registrationNo;
	@JsonProperty("DocumentTypeId")
	private String documentTypeId;
	@JsonProperty("FilePath")
	private String filePath;
	@JsonProperty("FileName")
	private String fileName;
	@JsonProperty("Description")
	private String description;
}
