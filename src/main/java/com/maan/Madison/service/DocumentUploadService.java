package com.maan.Madison.service;

import org.springframework.web.multipart.MultipartFile;

import com.maan.Madison.request.BuyPolicyRequest;
import com.maan.Madison.request.DocumentDeleteReq;
import com.maan.Madison.request.DocumentUploadGetReq;
import com.maan.Madison.request.DocumentUploadReq;
import com.maan.Madison.response.CommonResponse;

public interface DocumentUploadService {

	CommonResponse documentUpload(MultipartFile file, DocumentUploadReq req);

	CommonResponse getDocumentDetails(DocumentUploadGetReq req);

	String sendReferalQuoteMail(BuyPolicyRequest req);

	CommonResponse documentDelete(DocumentDeleteReq req);

}
