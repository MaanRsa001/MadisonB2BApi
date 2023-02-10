package com.maan.Madison.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AirtelPaymentResponse {
	
	private AirtelPaymentData data;
	
	private AirtelPaymentStatus status;
	
	private String error_description;
	
	private String error;

}
