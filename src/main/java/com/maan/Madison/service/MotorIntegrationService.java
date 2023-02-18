package com.maan.Madison.service;

import com.maan.Madison.request.MotorIntegrationRequest;
import com.maan.Madison.response.CommonResponse;

public interface MotorIntegrationService {

	CommonResponse integration(MotorIntegrationRequest req);

}
