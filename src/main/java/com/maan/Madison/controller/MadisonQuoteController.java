package com.maan.Madison.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maan.Madison.request.BuyPolicyRequest;
import com.maan.Madison.request.CustomerReq;
import com.maan.Madison.request.CustomerSaveReq;
import com.maan.Madison.request.MadisonQuoteRequest;
import com.maan.Madison.request.PolicyInfoRequest;
import com.maan.Madison.request.SaveVehicleReq;
import com.maan.Madison.request.VehDriverReq;
import com.maan.Madison.response.CommonResponse;
import com.maan.Madison.service.MadisonQuoteService;
import com.maan.Madison.utilityServiceImpl.ErrorList;
import com.maan.Madison.utilityServiceImpl.MadisonInputValidation;

@RestController
@RequestMapping("/madison")
public class MadisonQuoteController {
	

	@Autowired
	private MadisonQuoteService service;
	
	@Autowired
	private MadisonInputValidation validation;
	
	@PostMapping("/create/quote")
	public CommonResponse createQuote(@RequestBody MadisonQuoteRequest req) {
		List<ErrorList> list =validation.validateQuote(req.getQuoteRequest().getPolicyInfo());
		if(CollectionUtils.isEmpty(list)) {
			return service.createQuote(req);
		}else {
			CommonResponse r = new CommonResponse();
			r.setMessage("ERROR");
			r.setErrors(list);
			return r;
		}
	}
	
	@PostMapping("/getCustomerDetails")
	public CommonResponse getCustomerDetails(@RequestBody CustomerReq req) {
		return service.getCustomerDetails(req);
	}
	
	@GetMapping("/getCustomerById")
	public CommonResponse getCustomerById(@RequestParam ("customerId") String customerId) {
		return service.getCustomerById(customerId);
	}
	
	@PostMapping("/save/customer")
	public CommonResponse saveCustomer(@RequestBody CustomerSaveReq req) {
		return service.saveCustomer(req);
	}
	
	@PostMapping("/save/vehicle")
	public CommonResponse saveVehicle(@RequestBody SaveVehicleReq req) {
		return service.saveVehicle(req);
	}
	
	@GetMapping("/get/vehicleByApplicationId")
	public CommonResponse getVehicleByApplicationId(@RequestParam ("applicationNo") String applicationNo) {
		return service.getVehicleByApplicationId(applicationNo);
	}
	
	@GetMapping("/edit/vehicleById")
	public CommonResponse editVehicleById(@RequestParam ("applicationNo") String applicationNo,@RequestParam("vehicleId") String vehicleId) {
		return service.editVehicleById(applicationNo,vehicleId);
	}
	
	@GetMapping("/delete/vehicleById")
	public CommonResponse deleteVehicleById(@RequestParam ("applicationNo") String applicationNo,@RequestParam("vehicleId") String vehicleId) {
		return service.deleteVehicleById(applicationNo,vehicleId);
	}
		
	@PostMapping("/buypolicy")
	public CommonResponse doBuypolicy(@RequestBody BuyPolicyRequest req) {
		return service.doBuypolicy(req);
	}
	
	@PostMapping("/save/driver")
	public CommonResponse saveDriver(@RequestBody VehDriverReq req) {
		return service.saveDriver(req);
	}
	
	@GetMapping("/edit/driver")
	public CommonResponse editDriver(@RequestParam ("applicationNo") String applicationNo,@RequestParam("vehicleId") String vehicleId){
		return service.editDriver(applicationNo,vehicleId);	
	}
	
	@PostMapping("/validate/quote")
	public CommonResponse validateQuote(@RequestBody PolicyInfoRequest req) {
		CommonResponse res = new CommonResponse();
		List<ErrorList> list =validation.validateQuote(req);
		res.setMessage(CollectionUtils.isEmpty(list)?"SUCCESS":"ERROR");
		res.setErrors(list);
		return res;
	}

	@GetMapping("/get/RejectList")
	public CommonResponse getRejectList(@RequestParam ("loginId") String loginId,@RequestParam ("productId") String productId,@RequestParam ("branchCode") String branchCode) {
		return service.getRejectList(loginId,productId,branchCode);
	}
	
}
