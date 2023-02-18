package com.maan.Madison.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maan.Madison.request.BuyPolicyRequest;
import com.maan.Madison.request.DocumentDeleteReq;
import com.maan.Madison.request.DocumentUploadGetReq;
import com.maan.Madison.request.DocumentUploadReq;
import com.maan.Madison.response.CommonResponse;
import com.maan.Madison.service.DocumentUploadService;
import com.maan.Madison.serviceImpl.CommonService;

@RestController
@RequestMapping("/madison")
public class DocumentUploadController {
	
	Logger log =LogManager.getLogger(DocumentUploadController.class);
	
	@Autowired
	private DocumentUploadService service;
	@Autowired
	private CommonService cs;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@PostMapping("/document/upload")
	public CommonResponse documentUpload(@RequestBody List<DocumentUploadReq> req ) {	
	      return service.documentUpload(req);
	     
	}
	
	@PostMapping("/get/document/details")
	public CommonResponse getDocumentDetails(@RequestBody DocumentUploadGetReq req) {
		return service.getDocumentDetails(req);
	}

	@GetMapping("/document/download")
	public ResponseEntity<Resource> download(@RequestParam("FilePath") String filePath) throws IOException {
		File file = new File(filePath);

	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
	    
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
	            .contentLength(file.length())
	            .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
	            .body(resource);
	}
	
	
	@PostMapping("/send/mail")
	public String sendReferalQuoteMail(@RequestBody BuyPolicyRequest req) {
		return service.sendReferalQuoteMail(req);
	}
	
	@PostMapping("/document/delete")
	public CommonResponse documentDelete(@RequestBody DocumentDeleteReq req) {
		return service.documentDelete(req);
	}
}
