package com.maan.Madison.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AirtelPaymentStatus {
	
	private String response_code;
	private String code;

	private String success;

	private String result_code;

	private String message;
	
	

}