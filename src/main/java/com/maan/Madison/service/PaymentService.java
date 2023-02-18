package com.maan.Madison.service;

import com.maan.Madison.request.MadionPaymentRequest;
import com.maan.Madison.response.CommonResponse;

public interface PaymentService {

	CommonResponse doMtnPayment(MadionPaymentRequest req);

	CommonResponse doAirtelPayment(MadionPaymentRequest req);

	CommonResponse airtelPaymentStatus(String merchant_ref_no);

	CommonResponse cardPayment(MadionPaymentRequest req);

	CommonResponse mtnPaymentStatus(String reference_no);

	CommonResponse cashPayment(MadionPaymentRequest req);

	CommonResponse chequePayment(MadionPaymentRequest req);

}	
