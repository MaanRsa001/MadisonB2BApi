package com.maan.Madison.service;

import com.maan.Madison.request.DeductibleReq;
import com.maan.Madison.response.CommonResponse;

public interface DropDownService {

	CommonResponse getCurrencyType();

	CommonResponse getPolicyType();

	CommonResponse getPolicyDatys(String policyStartDate);

	CommonResponse getCustomerType();

	CommonResponse getTitle();

	CommonResponse getVehicleMake();

	CommonResponse getVehicleModel(String makeId);

	CommonResponse getManufactureyear();

	CommonResponse getVehicleColor();

	CommonResponse getNoOfClaims();

	CommonResponse getFinancebank();

	CommonResponse getDeductibles(DeductibleReq req);

	CommonResponse getNcbClaims(DeductibleReq req);

	CommonResponse getInsCompany();

	CommonResponse getCity();

	CommonResponse getDocuments(String productId);

	CommonResponse getBrokerBranchList(String loginId);

}
