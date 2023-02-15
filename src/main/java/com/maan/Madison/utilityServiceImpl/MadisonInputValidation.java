package com.maan.Madison.utilityServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.maan.Madison.request.BuyPolicyRequest;
import com.maan.Madison.request.CustomerSaveReq;
import com.maan.Madison.request.SaveVehicleReq;
import com.maan.Madison.response.BuyPolicyCustomerReq;
import com.maan.common.LogManager;
import com.maan.common.Validation;

@Component
public class MadisonInputValidation {
	
	private static final String NAME_EXP="[a-zA-Z][a-zA-Z]+";
	private static final String EMAIL_EXP="[a-z][a-zA-Z-0-9_.]+@[a-zA-Z0-9]([.][a-zA-Z])+";
	private static final String MOBILE_EXP="(0|91)?[7-9][0-9]{9}";
	private static final String DATE_EXP="[01][1-9]/[01][1-9]/[0-9]{4}";
	private static final String NUMBER_EXP="[0-9]{8}";
	private static final String CHASSISNO_EXP="";
	
	public List<ErrorList> validateCustomer(CustomerSaveReq req){
		List<ErrorList> list =new ArrayList<ErrorList>();
		try {
			
			if(StringUtils.isBlank(req.getTitle())) {
				list.add(new ErrorList("500","Title","Please choose title"));
			}if(StringUtils.isBlank(req.getFirstName())) {
				list.add(new ErrorList("500","FirstName","Please enter firstname"));
			}else if(!regularExp(NAME_EXP, req.getFirstName())) {
				list.add(new ErrorList("500","FirstName","Firstname should not contain any digits & special characters"));
			}
			if(StringUtils.isBlank(req.getMobileno())) {
				list.add(new ErrorList("500","Mobileno","Please enter mobilenumber"));
			}else if(!regularExp(MOBILE_EXP, req.getMobileno())) {
				list.add(new ErrorList("500","Mobileno","Mobilenumber allows only digits"));
			}
			if(StringUtils.isBlank(req.getEmail())) {
				list.add(new ErrorList("500","Email","Please enter email"));
			}else if(!regularExp(EMAIL_EXP,req.getEmail())){
				list.add(new ErrorList("500","Email","Please enter valid email"));
			}
			
			if(StringUtils.isBlank(req.getDateOfBirth())) {
				list.add(new ErrorList("500","CustomerType","Please enter dateofbirth"));
			}else if(!regularExp(DATE_EXP, req.getDateOfBirth())) {
				list.add(new ErrorList("500","CustomerType","Please enter valid dateofbirth"));
			}
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
			if(StringUtils.isBlank(req.getSumInsured())) {
				list.add(new ErrorList("500","SumInsured","Please enter suminsured"));
			}else if(!regularExp(NUMBER_EXP, req.getSumInsured())) {
				list.add(new ErrorList("500","SumInsured","Suminured allows only digits"));
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
			}
			if(StringUtils.isBlank(req.getChassisNo())) {
				list.add(new ErrorList("500","ChassisNo","Please enter chassisno"));
			}
			if(StringUtils.isBlank(req.getEngineNo())) {
				list.add(new ErrorList("500","EngineNo","Please enter engineno"));
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
	
	
	public List<ErrorList> validateCustomer(BuyPolicyRequest req){
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
					list.add(new ErrorList("101","DateOfBirth","DateOfBirth format should be like this :dd/mm/yyyy"));				
					}			
			}
			
			if(StringUtils.isEmpty(cus.getMobile()))
			{
				list.add(new ErrorList("101","Mobile","Please enter the mobilenumber"));
			}else if(!regularExp(MOBILE_EXP, cus.getMobile()))
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
				list.add(new ErrorList("101","Email","Please enter vaild email"));
			}
			if("INDIVIDUAL".equalsIgnoreCase(cus.getCustomerTyp())){
				
				if(StringUtils.isBlank(cus.getNrc())) {
					list.add(new ErrorList("101","NRC","Please enter nrc"));
				}if(StringUtils.isBlank(cus.getPassportNo())) {
					list.add(new ErrorList("101","PassportNo","Please enter passportno"));
				}
				
			}
			
			if(StringUtils.isBlank(cus.getCompanyRegNo())) {
				list.add(new ErrorList("101","CompanyRegNo","Please enter CompanyRegNo"));
			}else if (regularExp(MOBILE_EXP, cus.getCompanyRegNo())) {
				list.add(new ErrorList("101","CompanyRegNo","CompanyRegNo allows only digits"));
			}
				
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
		
	}
	

}
