package com.maan.Madison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maan.Madison.request.MadionPaymentRequest;
import com.maan.Madison.response.CommonResponse;
import com.maan.Madison.service.PaymentService;

@RestController
@RequestMapping("/madison")
public class PaymentController {
	
	@Autowired
	private PaymentService service;
	
	@PostMapping("/mtn/payment")
	public CommonResponse doMtnPayment(@RequestBody MadionPaymentRequest req) {
		return service.doMtnPayment(req);
	}
	
	@GetMapping("/mtn/payment/status")
	public CommonResponse mtnPaymentStatus(@RequestParam ("reference_no") String reference_no) {
		return service.mtnPaymentStatus(reference_no);
	}
	
	@PostMapping("/airtel/payment")
	public CommonResponse doAirtelPayment(@RequestBody MadionPaymentRequest req) {
		return service.doAirtelPayment(req);
	}
	
	@GetMapping("/aitel/payment/status")
	public CommonResponse airtelPaymentStatus(@RequestParam ("merchant_ref_no") String merchant_ref_no) {
		return service.airtelPaymentStatus(merchant_ref_no);
	}
	
	@PostMapping("/card/payment")
	public CommonResponse cardPayment(@RequestBody MadionPaymentRequest req) {
		return service.cardPayment(req);
	}
	

}
