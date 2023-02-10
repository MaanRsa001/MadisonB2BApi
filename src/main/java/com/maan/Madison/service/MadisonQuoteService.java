package com.maan.Madison.service;

import com.maan.Madison.request.BuyPolicyRequest;
import com.maan.Madison.request.CustomerReq;
import com.maan.Madison.request.CustomerSaveReq;
import com.maan.Madison.request.MadisonQuoteRequest;
import com.maan.Madison.request.SaveVehicleReq;
import com.maan.Madison.request.VehDriverReq;
import com.maan.Madison.response.CommonResponse;

public interface MadisonQuoteService {

	CommonResponse createQuote(MadisonQuoteRequest req);

	CommonResponse getCustomerDetails(CustomerReq req);

	CommonResponse getCustomerById(String customerId);

	CommonResponse saveCustomer(CustomerSaveReq req);

	CommonResponse saveVehicle(SaveVehicleReq req);

	CommonResponse getVehicleByApplicationId(String applicationNo);

	CommonResponse editVehicleById(String applicationNo, String vehicleId);

	CommonResponse deleteVehicleById(String applicationNo, String vehicleId);

	CommonResponse doBuypolicy(BuyPolicyRequest req);

	CommonResponse saveDriver(VehDriverReq req);

	CommonResponse editDriver(String applicationNo, String vehicleId);

}
