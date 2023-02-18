package com.maan.Madison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maan.Madison.request.MotorIntegrationRequest;
import com.maan.Madison.response.CommonResponse;
import com.maan.Madison.service.MotorIntegrationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/policy")
public class MotorIntegrationController {
	
	
	@Autowired
	private MotorIntegrationService service;
	
	
	@PostMapping("/integration")
	public CommonResponse integration(@RequestBody MotorIntegrationRequest req) {
		return service.integration(req);
	}
	
	
	

}
