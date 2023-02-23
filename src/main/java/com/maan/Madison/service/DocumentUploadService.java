package com.maan.Madison.service;

import java.util.List;

import com.maan.Madison.request.BuyPolicyRequest;
import com.maan.Madison.request.DocumentDeleteReq;
import com.maan.Madison.request.DocumentUploadGetReq;
import com.maan.Madison.request.DocumentUploadReq;
import com.maan.Madison.response.CommonResponse;

public interface DocumentUploadService {

	CommonResponse documentUpload(List<DocumentUploadReq> req);

	CommonResponse getDocumentDetails(DocumentUploadGetReq req);

	String sendReferalQuoteMail(BuyPolicyRequest req);

	CommonResponse documentDelete(DocumentDeleteReq req);

	CommonResponse getPolicyCertificate(String quoteNo, String vehicleId);

	CommonResponse getPolicySchedule(String quoteNo);

	CommonResponse getPolicyReceipt(String quoteNo);

	CommonResponse getPolicyDebit(String quoteNo);

}
