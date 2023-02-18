package com.maan.Madison.utilityServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.maan.Madison.request.BuyPolicyRequest;
import com.maan.Madison.request.CustomerSaveReq;
import com.maan.Madison.request.MadionPaymentRequest;
import com.maan.Madison.request.PolicyInfoRequest;
import com.maan.Madison.request.SaveVehicleReq;
import com.maan.Madison.request.VehDriverReq;
import com.maan.Madison.response.BuyPolicyCustomerReq;


@Component
public class MadisonInputValidation {
	
	private static final String NAME_EXP="[a-zA-Z][a-zA-Z]+";
	private static final String EMAIL_EXP="[a-z][a-zA-Z-0-9_.]+@[a-zA-Z0-9]([.][a-zA-Z])+";
	private static final String MOBILE_EXP="(09)?[7-9][0-9]{9}";
	private static final String DATE_EXP="[01][1-9]/[01][1-9]/[0-9]{4}";
	private static final String NUMBER_EXP="[0-9]*";
	private static final String CHASSISNO_EXP="[a-zA-Z0-9]{20}";
	
	public List<ErrorList> validateCustomer(CustomerSaveReq req){
		List<ErrorList> list =new ArrayList<ErrorList>();
		try {
			
			if(StringUtils.isBlank(req.getTitle())) {
				list.add(new ErrorList("500","Title","Please select title"));
			}if(StringUtils.isBlank(req.getFirstName())) {
				list.add(new ErrorList("500","FirstName","Please enter firstname"));
			}
			if(StringUtils.isBlank(req.getMobileno())) {
				list.add(new ErrorList("500","Mobileno","Please enter mobilenumber"));
			}else if(!StringUtils.isNumeric(req.getMobileno())) {
				list.add(new ErrorList("500","Mobileno","MobileNo allows only digits"));
			}
			if(StringUtils.isBlank(req.getEmail())) {
				list.add(new ErrorList("500","Email","Please enter email"));
			}//else if(!regularExp(EMAIL_EXP,req.getEmail())){
				//list.add(new ErrorList("500","Email","Please enter valid email"));
			//}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<ErrorList> validateVehicle(SaveVehicleReq req){
		List<ErrorList> list =new ArrayList<ErrorList>();
		try {
			
			if(StringUtils.isBlank(req.getMakeId())) {
				list.add(new ErrorList("500","Make","Please choose make"));
			}if(StringUtils.isBlank(req.getModelId())) {
				list.add(new ErrorList("500","ModelId","Please choose model"));
			}
			if(StringUtils.isBlank(req.getBodyTypeId())) {
				list.add(new ErrorList("500","BodyTypeId","Please enter bodytype"));
			}
			if("1".equalsIgnoreCase(req.getPolicyType())) {
				if(StringUtils.isBlank(req.getSumInsured())) {
					list.add(new ErrorList("500","SumInsured","Please enter suminsured"));
				}else if(!regularExp(NUMBER_EXP, req.getSumInsured())) {
					list.add(new ErrorList("500","SumInsured","Suminured allows only digits"));
				}else if(Double.parseDouble(req.getPolicyType())<=0) {
					list.add(new ErrorList("500","SumInsured","Suminured should be grater than 0."));

				}
			}
			if(StringUtils.isBlank(req.getVehicleUsage())) {
				list.add(new ErrorList("500","VehicleUsage","Please select vehicleusage"));
			}
			if(StringUtils.isBlank(req.getManufacureYear())) {
				list.add(new ErrorList("500","CustomerType","Please enter manufacureyear"));
			}
			if(StringUtils.isBlank(req.getExcessLimit())) {
				list.add(new ErrorList("500","ExcessLimit","Please choose excesslimit"));
			}
			if(StringUtils.isBlank(req.getRegistrationNo())) {
				list.add(new ErrorList("500","RegistrationNo","Please enter registrationno"));
			}else if(!StringUtils.isAlphanumeric(req.getRegistrationNo())) {
				list.add(new ErrorList("500","RegistrationNo","RegistrationNo does not allows special characters & length should be in 20 characters"));
			}
			if(StringUtils.isBlank(req.getChassisNo())) {
				list.add(new ErrorList("500","ChassisNo","Please enter chassisno"));
			}else if(!StringUtils.isAlphanumeric(req.getChassisNo())) {
				list.add(new ErrorList("500","ChassisNo","ChassisNo does not allows special characters & length should be in 20 characters"));
			}
			if(StringUtils.isBlank(req.getEngineNo())) {
				list.add(new ErrorList("500","EngineNo","Please enter engineno"));
			}else if(!StringUtils.isAlphanumeric(req.getEngineNo())) {
				list.add(new ErrorList("500","EngineNo","EngineNo does not allows special characters & length should be in 20 characters"));
			}
			
			if(StringUtils.isBlank(req.getSeatingCapacity())) {
				list.add(new ErrorList("500","SeatingCapacity","Please enter seating capacity"));
			}else if(!StringUtils.isNumeric(req.getSeatingCapacity())) {
				list.add(new ErrorList("500","SeatingCapacity","SeatingCapacity allows only digits"));

			}
			
			if(StringUtils.isBlank(req.getLeasedYn())) {
				list.add(new ErrorList("500","LeasedYn","Please choose LeasedYn"));
			}else if ("Y".equalsIgnoreCase(req.getLeasedYn()) && StringUtils.isBlank(req.getBankOfFinance())) {
				list.add(new ErrorList("500","BankOfFinance","Please choose bankOfFinance"));
			}
			
			if(StringUtils.isBlank(req.getPreviousClaimYN())) {
				list.add(new ErrorList("500","PreviousClaimYN","Please choose previousClaimYN"));
			}else if("Y".equals(req.getPreviousClaimYN()) && StringUtils.isBlank(req.getNoOfClaims())) {
				list.add(new ErrorList("500","NoOfClaims","Please choose noOfclaims"));

			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	private boolean regularExp(String regExp,String input) {
		try {
			Pattern p =Pattern.compile(regExp);
			Matcher m =p.matcher(input);
			boolean match=m.find();
			if(match && m.group().equals(input)) {
				return true;
			}	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public List<ErrorList> validateBuyPolicy(BuyPolicyRequest req){
		List<ErrorList> list = new ArrayList<ErrorList>();
		BuyPolicyCustomerReq cus =req.getCustomer();
		try{
			if(StringUtils.isEmpty(cus.getTitle()))
			{
				list.add(new ErrorList("101","Title","Please choose title"));
			}
			if(StringUtils.isEmpty(cus.getFirstName())) {
				list.add(new ErrorList("101","FirstName","Please enter firstname"));
			} else if(!regularExp(NAME_EXP, cus.getFirstName())){
				list.add(new ErrorList("101","FirstName","Firstname should not contain digits & special characters"));
			}
			
			if(StringUtils.isBlank(cus.getGender())) {
				list.add(new ErrorList("101","Gender","Please choose gender"));
			}
			
			if(StringUtils.isBlank(cus.getAddress1())){
				list.add(new ErrorList("101","Address1","Please choose address1"));
			}
			if(StringUtils.isBlank(cus.getCity()))
			{
				list.add(new ErrorList("101","City","Please enter city"));
			}
			if(StringUtils.isNotBlank(cus.getPoBox()) && !StringUtils.isAlphanumeric(cus.getPoBox())) {
				list.add(new ErrorList("101","PoBox","PoBox allows only digits"));
			}
			if(!"CORPORATE".equals(cus.getDateOfBirth())) {
				if(StringUtils.isBlank(cus.getDateOfBirth())) {
					list.add(new ErrorList("101","DateOfBirth","Plese enter dateOfBirth"));
				} else if(!regularExp(DATE_EXP, cus.getDateOfBirth())) {
					//list.add(new ErrorList("101","DateOfBirth","DateOfBirth format should be like this :dd/mm/yyyy"));				
					}			
			}
			
			if(StringUtils.isEmpty(cus.getMobile()))
			{
				list.add(new ErrorList("101","Mobile","Please enter the mobilenumber"));
			}else if(!StringUtils.isNumeric(cus.getMobile()))
			{
				list.add(new ErrorList("101","Mobile","MobileNumber allows only digits"));
			}
			
			else if(StringUtils.isNotBlank(cus.getAlternatMobileNo()) && !StringUtils.isNumeric(cus.getAlternatMobileNo()))
			{
				list.add(new ErrorList("101","AlternatMobileNo","Alternativemobile allows only digits"));

			}
		
			if(StringUtils.isEmpty(cus.getEmail())) {
				list.add(new ErrorList("101","Email","Please enter email"));
			}
			else if(!regularExp(EMAIL_EXP, cus.getEmail())) {	
				//list.add(new ErrorList("101","Email","Please enter vaild email"));
			}
			if("INDIVIDUAL".equalsIgnoreCase(cus.getCustomerTyp())){
				
				if(StringUtils.isBlank(cus.getNrc())) {
					list.add(new ErrorList("101","NRC","Please enter nrc"));
				}if(StringUtils.isBlank(cus.getPassportNo())) {
					//list.add(new ErrorList("101","PassportNo","Please enter passportno"));
				}
				
			}
			
			if(StringUtils.isBlank(cus.getCompanyRegNo())) {
				//list.add(new ErrorList("101","CompanyRegNo","Please enter CompanyRegNo"));
			}else if (!regularExp(MOBILE_EXP, cus.getCompanyRegNo())) {
				//list.add(new ErrorList("101","CompanyRegNo","CompanyRegNo allows only digits"));
			}
			
			if(StringUtils.isBlank(req.getPolicyStartDate())) {
				list.add(new ErrorList("101","PolicyStartDate","Please enter policy startdate"));
			}else if(!regularExp(DATE_EXP, req.getPolicyStartDate())) {
				list.add(new ErrorList("101","PolicyStartDate","Please enter valid policy startdate format : dd/mm/yyyy"));

			}
			if(StringUtils.isBlank(req.getPolicyEndDate())) {
				list.add(new ErrorList("101","PolicyEndDate","Please enter policy enddate"));
			}else if(!regularExp(DATE_EXP, req.getPolicyEndDate())) {
				list.add(new ErrorList("101","PolicyEndDate","Please enter valid policy enddate format : dd/mm/yyyy"));

			}
			
			if(StringUtils.isBlank(req.getReferalQuoteYn())) {
				list.add(new ErrorList("101","ReferalQuoteYn","Please choose referal quoteyn"));

			}else if ("Y".equalsIgnoreCase(req.getReferalQuoteYn()) &&  StringUtils.isBlank(req.getReferalRemarks())) {
				list.add(new ErrorList("101","ReferalQuoteYn","Please enter referal remarks"));
			}
			
			if(StringUtils.isBlank(req.getEmailQuoteYn())) {
				list.add(new ErrorList("101","EmailQuoteYn","Please choose email quoteyn"));
			}
			
			if(StringUtils.isBlank(req.getInstallmentYn())) {
				list.add(new ErrorList("101","InstallmentYn","Please choose installmentYn"));

			}
				
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<ErrorList> validateDriver(VehDriverReq req){
		List<ErrorList> list = new ArrayList<ErrorList>();
		try {
			if (StringUtils.isBlank(req.getDriverId())) {
				list.add(new ErrorList("101","License","Please enter license"));

			}else if(!StringUtils.isNumeric(req.getDriverId())){
				list.add(new ErrorList("101","License","License allows only digits"));
			}
			if (StringUtils.isBlank(req.getDriverDob())) {
				list.add(new ErrorList("101","DriverDob","Please enter driverDob"));
			}else if(!regularExp(DATE_EXP, req.getDriverDob())) {
				//list.add(new ErrorList("101","DriverDob","Please enter vaild driverDob .format should be like this .dd/mm/yyyy"));
			}
			
			if(StringUtils.isBlank(req.getOwnnerdriverYn())) {
				list.add(new ErrorList("101","OwnnerdriverYn","Please enter ownnerdriverYn"));
			}
			
			if(StringUtils.isBlank(req.getIsClaimDtl())) {
				list.add(new ErrorList("101","ClaimNCB","Please choose claimncb"));
			}
			
			/*else if("Y".equalsIgnoreCase(req.getIsClaimDtl())) {
				
				if(StringUtils.isBlank(req.getInsCompany())) {
					list.add(new ErrorList("101","InsCompany","Please choose insurance company"));
				}
				
				if(StringUtils.isBlank(req.getPolicyNo())) {
					list.add(new ErrorList("101","PolicyNo","Please choose previous policyno"));
				}
				
				if(StringUtils.isBlank(req.getPrePolicyExpDate())) {
					list.add(new ErrorList("101","PrePolicyExpDate","Please choose policyexpiry date"));
				}else if(!regularExp(DATE_EXP, req.getPrePolicyExpDate())) {
					list.add(new ErrorList("101","PrePolicyExpDate","Please enter vaild policyexpiry date .format should be like this .dd/mm/yyyy"));

				}
				
				if(StringUtils.isBlank(req.getClaimAmt())) {
					list.add(new ErrorList("101","ClaimAmt","Please choose claim amount"));
				}else if(!regularExp(NUMBER_EXP, req.getClaimAmt())) {
					list.add(new ErrorList("101","ClaimAmt","Claim amount allows only digits"));
				}
				
			}*/
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<ErrorList> validateQuote(PolicyInfoRequest req) {
		List<ErrorList> list = new ArrayList<ErrorList>();
		try {
			
			if(StringUtils.isBlank(req.getCurrencyType())) {
				list.add(new ErrorList("101","CurrencyType","Please select  currencytype"));
			}if(StringUtils.isBlank(req.getPolicyType())) {
				list.add(new ErrorList("101","PolicyType","Please select policytype"));
			}if(StringUtils.isBlank(req.getPolicyStartState())) {
				list.add(new ErrorList("101","PolicyStartState","Please select policy startdate"));
			}else if(!regularExp(DATE_EXP, req.getPolicyStartState())) {
				//list.add(new ErrorList("101","PolicyStartState","Please enter vaild policy startdate .format should be like this .dd/mm/yyyy"));

			}
			
			if(StringUtils.isBlank(req.getPolicyEndDate())) {
				list.add(new ErrorList("101","PolicyEndDate","Please select policy enddate"));

			}else if(!regularExp(DATE_EXP, req.getPolicyEndDate())) {
				//list.add(new ErrorList("101","PolicyEndDate","Please enter vaild policy enddate .format should be like this .dd/mm/yyyy"));

			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<ErrorList> vaildatePaymenttDetail(MadionPaymentRequest req){
		List<ErrorList> list = new ArrayList<ErrorList>();
		try {
			
			if("2".equals(req.getPaymentType())) {
				if(StringUtils.isEmpty(req.getChequeNo())){
					list.add(new ErrorList("101","ChequeNo","Please enter chequeno"));
				}else if(!StringUtils.isNumeric(req.getChequeNo())){
					list.add(new ErrorList("101","ChequeNo","Please enter valid chequeno"));
	
				}
				if(StringUtils.isEmpty(req.getChequeDate())){
					list.add(new ErrorList("101","ChequeDate","Please enter chequeDate"));
				} 
				 
				if(StringUtils.isEmpty(req.getChequeAmount())){
					list.add(new ErrorList("101","ChequeAmount","Please enter chequeAmount"));
				} else {
					if("Y".equalsIgnoreCase(req.getInstallmentYN())){
						/*if(Double.valueOf(insIntialAmount)>Double.valueOf(chequeAmount))
							list.add("error.payment.cheque.amount.invalid");
					}else{
						if(Double.valueOf(totalPremium)>Double.valueOf(chequeAmount))
							list.add("error.payment.cheque.amount.invalid");
						}*/
					}
				if(StringUtils.isEmpty(req.getBankName())){
					list.add(new ErrorList("101","BankName","Please enter bankName"));
				}
				if(StringUtils.isEmpty(req.getMicrCode())){
					list.add(new ErrorList("101","MicrCode","Please enter micrcode"));
				}else if(!StringUtils.isNumeric(req.getMicrCode())){
					list.add(new ErrorList("101","MicrCode","Please enter micrcode"));
				}else if(req.getMicrCode().length() < 9){
					list.add(new ErrorList("101","MicrCode","Micrcode length should below 9 digits or equal"));
				}
			}
		}else if("101".equalsIgnoreCase(req.getPaymentType())){
			if(StringUtils.isBlank(req.getMobileNo())){
				list.add(new ErrorList("101","MobileNo","Please enter mobileno"));
			}else if(!StringUtils.isNumeric(req.getMobileNo())){
				list.add(new ErrorList("101","MobileNo","MobileNo allows only digits"));
			}else if(!req.getMobileNo().startsWith("09") && !req.getMicrCode().startsWith("07")){
				list.add(new ErrorList("101","MobileNo","Mobileno should startwith 07 or 09"));
			}else if(req.getMobileNo().length()!=10){
				list.add(new ErrorList("101","MobileNo","Please enter valid mobileno"));
			}
		}
		else if("102".equalsIgnoreCase(req.getPaymentType())){
			if(StringUtils.isBlank(req.getMobileNo())){
				list.add(new ErrorList("101","MobileNo","Please enter mobileno"));
			}else if(!StringUtils.isNumeric(req.getMobileNo())){
				list.add(new ErrorList("101","MobileNo","MobileNo allows only digits"));
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
