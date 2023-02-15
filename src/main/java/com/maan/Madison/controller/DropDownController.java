package com.maan.Madison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maan.Madison.request.DeductibleReq;
import com.maan.Madison.response.CommonResponse;
import com.maan.Madison.service.DropDownService;


@RestController
@RequestMapping("/dropdown")
public class DropDownController {
	
	
	@Autowired
	private DropDownService service;
	
	
	@GetMapping("/get/currencytype")
	public CommonResponse getCurrencyType() {
		return service.getCurrencyType();
	}
	
	@GetMapping("/get/policytype")
	public CommonResponse getPolicyType() {
		return service.getPolicyType();
	}
	
	@GetMapping("/get/policydays")
	public CommonResponse getPolicyDatys(@RequestParam("policyStartDate") String policyStartDate) {
		return service.getPolicyDatys(policyStartDate);
	}
	
	@GetMapping("/get/customertype")
	public CommonResponse getCustomerType() {
		return service.getCustomerType();
	}
	
	@GetMapping("/get/title")
	public CommonResponse getTitle() {
		return service.getTitle();
	}
	
	@GetMapping("/get/vehiclemake")
	public CommonResponse getVehicleMake() {
		return service.getVehicleMake();
	}
	
	@GetMapping("/get/vehiclemodel")
	public CommonResponse getVehicleModel(@RequestParam ("makeId") String makeId) {
		return service.getVehicleModel(makeId);
	}
	
	@GetMapping("/get/manufactureyear")
	public CommonResponse getManufactureyear() {
		return service.getManufactureyear();
	}
	
	@GetMapping("/get/vehiclecolor")
	public CommonResponse getVehicleColor() {
		return service.getVehicleColor();
	}
	
	@GetMapping("/get/noOfClaims")
	public CommonResponse getNoOfClaims() {
		return service.getNoOfClaims();
	}
	
	@GetMapping("/get/financebank")
	public CommonResponse getFinancebank() {
		return service.getFinancebank();
	}
	
	@PostMapping("/get/deductible")
	public CommonResponse getDeductibles(@RequestBody DeductibleReq req) {
		return service.getDeductibles(req);
	}
	
	@PostMapping("/get/ncbclaims")
	public CommonResponse getNcbClaims(@RequestBody DeductibleReq req) {
		return service.getNcbClaims(req);
	}
	
	@GetMapping("/get/inscompany")
	public CommonResponse getInsCompany() {
		return service.getInsCompany();
	}
	
	@GetMapping("/get/city")
	public CommonResponse getCity() {
		return service.getCity();
	}
	
	@GetMapping("/get/documents")
	public CommonResponse getDocuments(@RequestParam("ProductId") String productId) {
		return service.getDocuments(productId);
	}
	
	@GetMapping("/get/broker/branch")
	public CommonResponse getBrokerBranchList(@RequestParam("loginId") String loginId) {
		return service.getBrokerBranchList(loginId);
	}
	
	
}
