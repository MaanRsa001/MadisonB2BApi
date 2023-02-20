package com.maan.Madison.serviceImpl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maan.Madison.entity.Payment_Detail;
import com.maan.Madison.repository.PaymentRepository;
import com.maan.Madison.request.AirtelPayTokenRequest;
import com.maan.Madison.request.AirtelPaymentRequest;
import com.maan.Madison.request.MadionPaymentRequest;
import com.maan.Madison.request.MotorIntegrationRequest;
import com.maan.Madison.request.PayeeRequest;
import com.maan.Madison.request.PaymentRequest;
import com.maan.Madison.request.SubscriberReq;
import com.maan.Madison.request.TransactionReq;
import com.maan.Madison.response.AirtelPaymentResponse;
import com.maan.Madison.response.CardPaymentRespone;
import com.maan.Madison.response.CommonResponse;
import com.maan.Madison.service.MotorIntegrationService;
import com.maan.Madison.service.PaymentService;
import com.maan.Madison.utilityServiceImpl.ErrorList;
import com.maan.Madison.utilityServiceImpl.MadisonInputValidation;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	Logger log =LogManager.getLogger(getClass());
	private static final String HMAC_SHA256 = "HmacSHA256";
	
	@Autowired
	private PaymentRepository paymentRepo;
	@Autowired
	private CommonService commondao;
	@Autowired
	private MotorIntegrationService integration;
	@Autowired
	private MadisonInputValidation validation;
	
	ObjectMapper mapper = new ObjectMapper();
	
	
	@Override
	public CommonResponse doMtnPayment(MadionPaymentRequest req) {
		log.info("doMtnPayment request"+commondao.reqPrint(req));
		CommonResponse res = new CommonResponse();
		String paymentStatus="";
		try {
			String merchantRefNo=insertPaymentDetails(req);
			if(StringUtils.isNotBlank(merchantRefNo)) {
				List<Map<String,Object>>paymentList=paymentRepo.getPaymentDetByMerchantRefNo(merchantRefNo);
					Map<String,Object> mpd =paymentList.get(0);
					String quoteNo = mpd.get("QUOTE_NO")==null?"":mpd.get("QUOTE_NO").toString();
					String productId = mpd.get("PRODUCT_ID")==null?"":mpd.get("PRODUCT_ID").toString();
					String premium = mpd.get("PREMIUM")==null?"":mpd.get("PREMIUM").toString();
					String currencyType = mpd.get("CURRENCY_TYPE")==null?"":mpd.get("CURRENCY_TYPE").toString();
					String referenceNo = mpd.get("REFERENCE_NO")==null?"":mpd.get("REFERENCE_NO").toString();
					String mobileNo = mpd.get("MTN_MOBILE_NO")==null?"":mpd.get("MTN_MOBILE_NO").toString();
					
					if(StringUtils.isNotBlank(quoteNo) && StringUtils.isNotBlank(premium)
							&& StringUtils.isNotBlank(merchantRefNo) && StringUtils.isNotBlank(currencyType)
							&& StringUtils.isNotBlank(referenceNo) && StringUtils.isNotBlank(mobileNo)){
						
						PayeeRequest payeeReq =PayeeRequest.builder()
								.partyId("MSISDN")
								.partyIdType(!mobileNo.startsWith("26")?"26"+mobileNo:mobileNo)
								.build();
			
						PaymentRequest paymentReq =PaymentRequest.builder()
								.externalId(quoteNo)
								.currency(currencyType)
								.amount(premium)
								.payeeNote("Payment for Madison")
								.payerMessage("Pay for Insurance")
								.payer(payeeReq)
								.build();
						
						String reference_no=mtnRequestPay(paymentReq, referenceNo,merchantRefNo);	
						
						try {
							Thread.sleep(5000);
						}catch (Exception e) {
							e.printStackTrace();
						}
						
						res =mtnPaymentStatus(reference_no);
						Map<String,Object> paymentStatusRes =(Map<String,Object>)res.getResponse();
						paymentStatus =paymentStatusRes.get("status")==null?"":paymentStatusRes.get("status").toString();
						int count =0;
						if("Success".equalsIgnoreCase(paymentStatus)) {
							 count=paymentRepo.updateRequestPayStatus("S", merchantRefNo, quoteNo);							 
						}else if("Pending".equalsIgnoreCase(paymentStatus)) {
							 count=paymentRepo.updateRequestPayStatus("P", merchantRefNo, quoteNo);
						}else if ("Failed".equalsIgnoreCase(paymentStatus)) {
							 count=paymentRepo.updateRequestPayStatus("F", merchantRefNo, quoteNo);
						}else {
							 count=paymentRepo.updateRequestPayStatus("E", merchantRefNo, quoteNo);
						}
						
						log.info("doMtnPayment payment status update :"+count);

					}
					
				}
			res.setMessage(paymentStatus);
			res.setResponse(StringUtils.isBlank(paymentStatus)?"Server busy please try again later!":paymentStatus);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return res;
	}
	
	private String insertPaymentDetails(MadionPaymentRequest req) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String cheque_cash_no="";String merchant_reference="";
		String bankname="";
		String amount ="";
		String cheque_or_cashDate="";
		String micr_code="";
		String noOfTerms="";
		String paymentPremiumDate=""; String installmentNo="";String description=""; String Insat_totalPremium="";
		try {
			
			Map<String,Object> map=paymentRepo.getPaymentInsertDetails(req.getQuoteNo(),req.getProductId(),req.getBranchCode());
			String paymentMode=req.getPaymentType();
			merchant_reference=paymentRepo.getMerchantRefNo();
			log.info("Insert Payment || Start ||MerchantRefNo : "+merchant_reference);
			if("1".equals(paymentMode)) {
				//cheque_cash_no =StringUtils.isBlank(req.getCashChellanNo())?"":req.getCashChellanNo();
				//bankname=StringUtils.isBlank(req.getCashDepositBank())?"":req.getCashDepositBank();
				//amount=StringUtils.isBlank(req.getCashAmount())?"":req.getCashAmount();
				//cheque_or_cashDate=StringUtils.isBlank(req.getCashInstrumentDate())?"":req.getCashInstrumentDate();
				
			}else if("2".equals(paymentMode)) {
				cheque_cash_no=req.getChequeNo();
				bankname= req.getBankName();
				micr_code =req.getMicrCode();
				amount=req.getChequeAmount();
				cheque_or_cashDate=req.getChequeDate();
			}
			
			if(req.getInstallmentYN().equals("Y")) {
				List<Map<String,Object>> installmentDetailsList =paymentRepo.getInstallmentDet(req.getQuoteNo());
				if(!CollectionUtils.isEmpty(installmentDetailsList)) {
					Insat_totalPremium = installmentDetailsList.get(0).get("PREMIUM_AMOUNT")==null?"":installmentDetailsList.get(0).get("PREMIUM_AMOUNT").toString();
					noOfTerms = installmentDetailsList.get(0).get("NO_OF_TERMS")==null?"":installmentDetailsList.get(0).get("NO_OF_TERMS").toString();
					paymentPremiumDate = installmentDetailsList.get(0).get("PAYMENT_PREMIUM_DATE")==null?"":installmentDetailsList.get(0).get("PAYMENT_PREMIUM_DATE").toString();
					installmentNo = installmentDetailsList.get(0).get("INSTALLMENT_NO")==null?"":installmentDetailsList.get(0).get("INSTALLMENT_NO").toString();
					description = installmentDetailsList.get(0).get("DESCRIPTION")==null?"":installmentDetailsList.get(0).get("DESCRIPTION").toString();
					
				}
			}else {
				Insat_totalPremium="1";
			}
			
			String airtel_mtn_mobileNo="";String universalId="";
			if("101".equalsIgnoreCase(paymentMode)) {
				universalId = getUniversalId();
				airtel_mtn_mobileNo = req.getMobileNo();
			}else if("102".equalsIgnoreCase(paymentMode)) {
				universalId = merchant_reference;
				airtel_mtn_mobileNo = req.getMobileNo();
			}
			
			Payment_Detail payment =Payment_Detail.builder()
					.application_no(map.get("APPLICATION_NO")==null?"":map.get("APPLICATION_NO").toString())
					.quote_no(map.get("QUOTE_NO")==null?null:Long.valueOf(map.get("QUOTE_NO").toString()))
					.product_id(Long.valueOf(req.getProductId()))
					.payment_type(paymentMode)
					.merchant_reference(merchant_reference)
					.customer_email(map.get("EMAIL")==null?"":map.get("EMAIL").toString())
					.customer_name(map.get("CUST_NAME")==null?"":map.get("CUST_NAME").toString())
					.branch_code(StringUtils.isBlank(req.getBranchCode())?"":req.getBranchCode())
					.currency_type(map.get("CURRENCY_TYPE")==null? "ZMW":map.get("CURRENCY_TYPE").toString())
					.installment_yn(StringUtils.isBlank(req.getInstallmentYN())?"":req.getInstallmentYN())
					.premium(map.get("OVERALL_PREMIUM")==null?null:Double.valueOf(map.get("OVERALL_PREMIUM").toString()))
					.no_of_installment(StringUtils.isBlank(noOfTerms)?null:Long.valueOf(noOfTerms))
					.installment_amount(Double.valueOf(Insat_totalPremium))
					//.installment_frequency("monthly")
					.installment_start_date(paymentPremiumDate)
					//.installment_no(Long.valueOf(installmentNo))
					.installment_remarks(description)
					.mobile_no(map.get("MOBILE")==null?"":map.get("MOBILE").toString())
					//.device_type("Mobile")
					.cheque_no(cheque_cash_no)
					.bank_name(bankname)
					.micr_code(micr_code)
					.cheque_amount(StringUtils.isBlank(amount)?null:Double.valueOf(amount))
					.cheque_date(StringUtils.isBlank(cheque_or_cashDate)?null:sdf.parse(cheque_or_cashDate))
					.request_time(new Date())
					.reference_no(universalId)
					.mtnMobileNo(airtel_mtn_mobileNo)
					
					.build();
			
			Payment_Detail payment_Detail =paymentRepo.save(payment);
			log.info("Insert Payment || End ||MerchantRefNo : "+payment_Detail.getMerchant_reference());
			List<Map<String,Object>> list =paymentRepo.getPersonalDetailsByQuoteNo(req.getQuoteNo());
			Map<String,Object> personal=list.get(0);
			payment_Detail.setBill_to_forename(personal.get("FIRST_NAME")==null?"":String.valueOf(personal.get("FIRST_NAME")));
			payment_Detail.setBill_to_surname(personal.get("LAST_NAME")==null?"":String.valueOf(personal.get("LAST_NAME")));
			payment_Detail.setBill_to_address_line1(personal.get("ADDRESS1")==null?"":String.valueOf(personal.get("ADDRESS1")));
			String emirate =personal.get("EMIRATE")==null?"":String.valueOf(personal.get("EMIRATE"));
			String city =personal.get("CITY")==null?"":String.valueOf(personal.get("CITY"));
			payment_Detail.setBill_to_address_city(StringUtils.isBlank(emirate)?city:emirate);
			payment_Detail.setBill_to_address_country("ZM");
			payment_Detail.setBill_to_address_postal_code(personal.get("POBOX")==null?"":String.valueOf(personal.get("POBOX")));
			payment_Detail.setBill_to_email(personal.get("EMAIL")==null?"":String.valueOf(personal.get("EMAIL")));
			paymentRepo.saveAndFlush(payment_Detail);
			
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return merchant_reference;
	}
	
	private String getUniversalId() {
		String universelId ="";
		try {
			String api =commondao.getappconstantsProperty().getProperty("Get.universal.api").trim();
			log.info("Calling UniverselId Api :"+api );
			RestTemplate restTemplate = new RestTemplate();
			universelId = restTemplate.getForObject(api, String.class);
			log.info("Universel Api Response :"+ universelId);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return universelId;
	}
	
	@SuppressWarnings("unchecked")
	public String mtnRequestPay(Object request,String referenceNo, String merchantRefNo) {
		log.info("mtnRequestPay Response  || Enter || Request || "+commondao.reqPrint(request));
		String targetEnvironment =commondao.getappconstantsProperty().getProperty("target.environment").trim();
		String subscriptionKey =commondao.getappconstantsProperty().getProperty("subscription.key").trim();
		String api =commondao.getappconstantsProperty().getProperty("mtn.request.to.pay.url").trim();
		try {
			ResponseEntity<String> resEnt = null;
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", getMtnJwtToken());
			headers.set("X-Reference-Id", referenceNo); 
			headers.set("X-Target-Environment", targetEnvironment);
			headers.set("Ocp-Apim-Subscription-Key", subscriptionKey);
			HttpEntity<Object> entityReq = new HttpEntity<>(request, headers);
			resEnt = restTemplate.postForEntity(api, entityReq, String.class);
			log.info("mtnRequestPay || End || Response code || "+resEnt.getStatusCodeValue());
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return referenceNo ;
	}
	
	
	public int saveApiDetails(String merchantRefId,String apiDesc,String method,String api,String headers,
			String request,String responseStatus,String response,String errorResponse) {
		try {
			return paymentRepo.insertApiDetails(merchantRefId,apiDesc,method,api,headers,request,responseStatus,response,errorResponse);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public CommonResponse doAirtelPayment(MadionPaymentRequest req) {
		CommonResponse res = new CommonResponse();
		AirtelPaymentResponse payRes =new AirtelPaymentResponse();
		List<ErrorList> error = new ArrayList<ErrorList>();
		String paymentStatus="";
		try {
			String merchantRefNo=insertPaymentDetails(req);
			if(StringUtils.isNotBlank(merchantRefNo)) {
				List<Map<String,Object>>paymentList=paymentRepo.getPaymentDetByMerchantRefNo(merchantRefNo);
					Map<String,Object> mpd =paymentList.get(0);
					String quoteNo = mpd.get("QUOTE_NO")==null?"":mpd.get("QUOTE_NO").toString();
					String productId = mpd.get("PRODUCT_ID")==null?"":mpd.get("PRODUCT_ID").toString();
					String premium = mpd.get("PREMIUM")==null?"":mpd.get("PREMIUM").toString();
					String currencyType = mpd.get("CURRENCY_TYPE")==null?"":mpd.get("CURRENCY_TYPE").toString();
					String referenceNo = mpd.get("REFERENCE_NO")==null?"":mpd.get("REFERENCE_NO").toString();
					String mobileNo = mpd.get("MTN_MOBILE_NO")==null?"":mpd.get("MTN_MOBILE_NO").toString();
					
					if(StringUtils.isNotBlank(quoteNo) && StringUtils.isNotBlank(premium)
							&& StringUtils.isNotBlank(merchantRefNo) && StringUtils.isNotBlank(currencyType)
							&& StringUtils.isNotBlank(referenceNo) && StringUtils.isNotBlank(mobileNo)){
						
						SubscriberReq subscriberReq = SubscriberReq.builder()
								.country("ZM")
								.currency(currencyType)
								.msisdn(Integer.valueOf(mobileNo))
								.build();
						TransactionReq transactionReq =TransactionReq.builder()
								.amount(premium)
								.currency(currencyType)
								.country("ZM")
								.id(merchantRefNo)
								.build();
						AirtelPaymentRequest paymentReq = AirtelPaymentRequest.builder()
								.reference("Madison Gen")
								.subscriber(subscriberReq)
								.transaction(transactionReq)
								.build();
						
						// Airtel Request pay calling
						callAirtelRequestPay(paymentReq);
						try {
							Thread.sleep(5000);
						}catch (Exception e) {
							e.printStackTrace();
						}
						
						payRes=checkAirtelPaymentStatus(merchantRefNo);
						paymentStatus=StringUtils.isBlank(payRes.getData().getTransaction().getStatus())?"":payRes.getData().getTransaction().getStatus();
						
						if("TIP".equalsIgnoreCase(paymentStatus)) {
							paymentRepo.updatePaymentStatus("P",merchantRefNo,quoteNo,productId);
						}else if("TF".equalsIgnoreCase(paymentStatus)) {
							paymentRepo.updatePaymentStatus("F",merchantRefNo,quoteNo,productId);
						}else if ("TS".equalsIgnoreCase(paymentStatus)) {
							paymentRepo.updatePaymentStatus("S",merchantRefNo,quoteNo,productId);
						}else {
							paymentRepo.updatePaymentStatus("E",merchantRefNo,quoteNo,productId);
						}
						
					}
					
					if("TS".equalsIgnoreCase(paymentStatus)) {
						MotorIntegrationRequest inteReq =new MotorIntegrationRequest();
						inteReq.setQuoteNo(req.getQuoteNo());
						inteReq.setBranchCode(req.getBranchCode());
						inteReq.setProductId(req.getProductId());
						res=integration.integration(inteReq);
					}else {
						res.setMessage("ERROR");
						error.add(new ErrorList("100","AirtelPayment","We have not received amount for this quotation "+req.getQuoteNo()+" "));
						res.setErrors(error);
					}
					
			}else {
				res.setMessage("ERROR");
				error.add(new ErrorList("100","AirtelPayment","Something went wrong in server .Please contact admin...!"));
				res.setErrors(error);			
				
			}
			}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			res.setMessage("ERROR");
			error.add(new ErrorList("100","AirtelPayment",e.getMessage()));
			return res;
		}
		return res;
	}
	@Override
	public CommonResponse airtelPaymentStatus(String merchant_ref_no) {
		CommonResponse res = new CommonResponse();
		try {
			AirtelPaymentResponse payRes=checkAirtelPaymentStatus(merchant_ref_no);
			res.setResponse(payRes);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return res;
	}

	
	public AirtelPaymentResponse callAirtelRequestPay(AirtelPaymentRequest paymentReq) {
		AirtelPaymentResponse res =null;
		try {
			log.info("getAirteLPayTokenCall || End || Response  ||"+commondao.reqPrint(paymentReq));
			String currency_type =commondao.getappconstantsProperty().getProperty("air.currency.type");
			String country=commondao.getappconstantsProperty().getProperty("air.country");
			String api =commondao.getappconstantsProperty().getProperty("airtel.payment.url");
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", getAirtelJwtToken());
			headers.set("Content-Type", "application/json");
			headers.set("X-Country", country);
			headers.set("X-Currency", currency_type);
			HttpEntity<Object> request =new HttpEntity<Object>(paymentReq,headers);
			ResponseEntity<String> resEnt = restTemplate.postForEntity(api, request, String.class);
			String response =resEnt.getBody();
			String status_code=String.valueOf(resEnt.getStatusCodeValue());
			res =mapper.readValue(response, AirtelPaymentResponse.class);
			log.info("callAirtelRequestPay || Response || "+commondao.reqPrint(res));
			String apiResponse=commondao.reqPrint(res);
			String apiHeaders=commondao.reqPrint(headers);
			saveApiDetails(paymentReq.getTransaction().getId(), "AIRTEL_REQUEST_PAYMENT", "POST", api, apiHeaders, commondao.reqPrint(paymentReq), status_code, apiResponse, "");
		}catch (Exception e) {

			log.error(e);
			e.printStackTrace();
		}
		return res;
		
	}
	
	
	@SuppressWarnings("unchecked")
	private String getAirteLPayTokenCall(){
		Map<String,Object> data =null;
		String response ="";
		String client_id =commondao.getappconstantsProperty().getProperty("air.token.client.id");
		String client_secret=commondao.getappconstantsProperty().getProperty("air.token.secret.id");
		String grand_type=commondao.getappconstantsProperty().getProperty("air.token.geran.type");
		String api =commondao.getappconstantsProperty().getProperty("air.token.api");
		try {
			AirtelPayTokenRequest request =AirtelPayTokenRequest.builder()
					.client_id(client_id)
					.client_secret(client_secret)
					.grant_type(grand_type)
					.build();
			ResponseEntity<String> resEnt = null;
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			HttpEntity<Object> headerReq = new HttpEntity<>(request,headers);
			resEnt = restTemplate.postForEntity(api, headerReq, String.class);
			String res =resEnt.getBody();
			ObjectMapper maper= new ObjectMapper();
			data =maper.readValue(res, Map.class);
			String token=data.get("access_token").toString();
			String expires_in=data.get("expires_in").toString();
			response ="Bearer "+token;
			log.info("getAirteLPayTokenCall Token"+response);
			log.info("getAirteLPayTokenCall || End || Response  ||"+commondao.reqPrint(data));
			paymentRepo.insert_jwt_token("AIRTEL_PAYMENT","Bearer",response,expires_in);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return response;
	}


	public AirtelPaymentResponse checkAirtelPaymentStatus(String merchant_ref_no) {
		AirtelPaymentResponse res = new AirtelPaymentResponse();
		try {
			String currency_type =commondao.getappconstantsProperty().getProperty("air.currency.type");
			String country=commondao.getappconstantsProperty().getProperty("air.country");
			String api=commondao.getappconstantsProperty().getProperty("airtel.payment.status");
			String request_api = api+"/"+merchant_ref_no;
			log.info("airtelPaymentStatus || api ||"+request_api);
			RestTemplate restTemplate =new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", getAirtelJwtToken());
			headers.set("X-Country", country);
			headers.set("X-Currency", currency_type);
			HttpEntity<Void> request =new HttpEntity<Void>(headers);
			ResponseEntity<String> resEnt=restTemplate.exchange(request_api,HttpMethod.GET,request,String.class);
			String response =resEnt.getBody();
			String responseCode=String.valueOf(resEnt.getStatusCodeValue());
			log.info("airtelPaymentStatus || Response || "+response+"|| ResponseCode || "+responseCode);
			res =mapper.readValue(response, AirtelPaymentResponse.class);
			saveApiDetails(merchant_ref_no, "AIRTEL_PAYMENT_STATUS", "GET", api, commondao.reqPrint(headers), null, responseCode, response, "");

		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return res;
		
	}
		
	private String getAirtelJwtToken() {
		String token ="";
		try {
			token =paymentRepo.getAirtelJwtToken();
			
			if(StringUtils.isBlank(token)) {
				token =getAirteLPayTokenCall();
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return token;
	}
	
	public String  getMtnPaymentToken() {
		String token="";		
		try {
			String subscription_key =commondao.getappconstantsProperty().getProperty("subscription.key");
			String basic_auth=commondao.getappconstantsProperty().getProperty("basic.auth");
			String api=commondao.getappconstantsProperty().getProperty("mtn.access.token.url");
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", basic_auth);
			headers.set("Ocp-Apim-Subscription-Key", subscription_key);		
			HttpEntity<Void> header =new HttpEntity<Void>(headers);
			ResponseEntity<String> resEnt=restTemplate.exchange(api,HttpMethod.POST,header,String.class);
			String apiRes=resEnt.getBody();
			Map<String,Object> map=mapper.readValue(apiRes, Map.class);
			//saveApiDetails(merchant_ref_no, "MTN_PAYMENT_TOKEN", "POST", api, commondao.reqPrint(headers), null, responseCode, apiRes, "");
			token ="Bearer "+map.get("access_token")==null?"":map.get("access_token").toString();
			String expires_in =map.get("expires_in")==null?"":map.get("expires_in").toString();
			paymentRepo.insert_mtn_token("MTN_PAYMENT","Bearer",token,expires_in);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return token;
		
	}

	@Override
	public CommonResponse cardPayment(MadionPaymentRequest req) {
		CommonResponse res = new CommonResponse();
		String signature="";
		try {
			String merchant_ref_no=insertPaymentDetails(req);
			Map<String,Object> payDet =paymentRepo.getPendingPayDet(merchant_ref_no);
			String currencyType=payDet.get("CURRENCY_TYPE")==null? "ZMW":payDet.get("CURRENCY_TYPE").toString();
			String installmentYn=payDet.get("INSTALLMENT_YN")==null?"":payDet.get("INSTALLMENT_YN").toString();
			Map<String,Object> payMas=paymentRepo.getPaymentMasterByCurrencyType(currencyType);
			CardPaymentRespone cardRes =CardPaymentRespone.builder()
					.accessKey(payMas.get("ACCESS_KEY")==null?"":payMas.get("ACCESS_KEY").toString())
					.address(payDet.get("BILL_TO_ADDRESS_LINE1")==null?"":payDet.get("BILL_TO_ADDRESS_LINE1").toString())
					.amount(payDet.get("PREMIUM")==null?"":payDet.get("PREMIUM").toString())
					.city(payDet.get("BILL_TO_ADDRESS_CITY")==null?"":payDet.get("BILL_TO_ADDRESS_CITY").toString())
					.country(payDet.get("BILL_TO_ADDRESS_COUNTRY")==null?"":payDet.get("BILL_TO_ADDRESS_COUNTRY").toString())
					.currency(payDet.get("CURRENCY_TYPE")==null?"":payDet.get("CURRENCY_TYPE").toString())
					.email(payDet.get("BILL_TO_EMAIL")==null?"":payDet.get("BILL_TO_EMAIL").toString())
					.firstName(payDet.get("BILL_TO_FORENAME")==null?"":payDet.get("BILL_TO_FORENAME").toString())
					.postalCode(payDet.get("BILL_TO_ADDRESS_POSTAL_CODE")==null?"":payDet.get("BILL_TO_ADDRESS_POSTAL_CODE").toString())
					.profileId(payMas.get("PROFILE_ID")==null?"":payMas.get("PROFILE_ID").toString())
					.recurring_amount(payDet.get("INSTALLMENT_AMOUNT")==null?"":payDet.get("INSTALLMENT_AMOUNT").toString())
					.recurringFrequency(payDet.get("INSTALLMENT_FREQUENCY")==null?"":payDet.get("INSTALLMENT_FREQUENCY").toString())
					.recurringStartDate(payDet.get("INSTALLMENT_START_DATE")==null?"":payDet.get("INSTALLMENT_START_DATE").toString())
					.referenceNo(merchant_ref_no)
					.surName(payDet.get("BILL_TO_SURNAME")==null?"":payDet.get("BILL_TO_SURNAME").toString())
					.SecretKey(payMas.get("SECRET_KEY")==null?"":payMas.get("SECRET_KEY").toString())
					.noOfInstallment(payDet.get("NO_OF_INSTALLMENT")==null?"":payDet.get("NO_OF_INSTALLMENT").toString())
					.locale("en")
					.transactionUuid(merchant_ref_no)
					.installmentYN(installmentYn)
					.build();
			signature=generateSignature(cardRes);
			cardRes.setSignature(signature);
			if("Y".equals(installmentYn)) {
				cardRes.setTransactionType("sale,create_payment_token");
			}else {
				cardRes.setTransactionType("sale");
			}
			cardRes.setRecurring_automatic_renew("false");
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return res;
	}
	
	private String generateSignature(CardPaymentRespone res) throws Exception {
		String signedName ="access_key,profile_id,transaction_uuid,signed_field_names,unsigned_field_names,signed_date_time,locale,transaction_type,reference_number,amount,currency,recurring_amount,recurring_automatic_renew,recurring_frequency,recurring_start_date,recurring_number_of_installments";
		String unsignedName="bill_to_forename,bill_to_surname,bill_to_address_line1,bill_to_address_city,bill_to_address_country,bill_to_address_postal_code,bill_to_email";
		String signature="";
		try {
			HashMap<String,String> params = new HashMap<String,String>();
			params.put("amount", res.getAmount());
			params.put("reference_number", res.getReferenceNo());
			params.put("signed_field_names", signedName);
			params.put("profile_id", res.getProfileId());
			params.put("signed_date_time",new SimpleDateFormat("dd/mm/yyyy").format(new Date()));
			params.put("transaction_type", res.getInstallmentYN().equals("Y")?"sale,create_payment_token":"sale");
			params.put("locale", res.getLocale());
			params.put("transaction_uuid", res.getTransactionUuid());
			params.put("access_key", res.getAccessKey());
			params.put("unsigned_field_names", unsignedName);
			params.put("currency", res.getCurrency());
			params.put("recurring_amount", res.getRecurring_amount());
			params.put("recurring_frequency", res.getRecurringFrequency());
			params.put("recurring_start_date", res.getRecurringStartDate());
			params.put("recurring_number_of_installments", res.getNoOfInstallment());
			params.put("recurring_automatic_renew", "false");
			
			StringJoiner stringJoiner =new StringJoiner(",");
			String [] signedArray =signedName.split(",");
			for(String s :signedArray) {
				stringJoiner.add(s + "=" + String.valueOf(params.get(s)));
			}
			SecretKeySpec secretKeySpec = new SecretKeySpec(res.getSecretKey().getBytes(), HMAC_SHA256);
		    Mac mac = Mac.getInstance(HMAC_SHA256);
		    mac.init(secretKeySpec);
		    byte[] rawHmac = mac.doFinal(String.valueOf(stringJoiner).getBytes("UTF-8"));
		    DatatypeConverter.printBase64Binary(rawHmac).replace("\n", "");	
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return signature;
	}


	@Override
	public CommonResponse mtnPaymentStatus(String reference_no) {
		CommonResponse res =new CommonResponse();
		try {
			String subscription_key =commondao.getappconstantsProperty().getProperty("subscription.key");
			String target_environment =commondao.getappconstantsProperty().getProperty("target.environment");
			String api=commondao.getappconstantsProperty().getProperty("mtn.request.to.pay.url")+"/"+reference_no;
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", getMtnJwtToken());
			headers.set("X-Target-Environment", target_environment);
			headers.set("Ocp-Apim-Subscription-Key", subscription_key);
			HttpEntity<Void> entity =new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> apiRes =restTemplate.exchange(api, HttpMethod.GET, entity, String.class);
			String payRes=apiRes.getBody();
			log.info("mtnPaymentStatus || "+payRes);
			Map<String,Object> map =mapper.readValue(payRes, Map.class);
			String status =map.get("status")==null?"":map.get("status").toString();
			log.info("mtnPaymentStatus || "+status);
			res.setResponse(map);
			saveApiDetails(reference_no, "MTN_PAYMENT_STATUS", "GET", api, mapper.writeValueAsString(headers), "", String.valueOf(apiRes.getStatusCodeValue()), payRes, "");
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return res;
	}
	
	
	private String getMtnJwtToken() {
		String token ="";
		try {
			token =paymentRepo.getMtnJwtToken();
			if(StringUtils.isBlank(token)) {
				token =getMtnPaymentToken();
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return token;
	}

	@Override
	public CommonResponse cashPayment(MadionPaymentRequest req) {
		CommonResponse res = new CommonResponse();
		List<ErrorList> list =new ArrayList<ErrorList>();
		try {
			String merchantRefNo=insertPaymentDetails(req);
			if(StringUtils.isNotBlank(merchantRefNo)) {
				paymentRepo.updateRequestPayStatus("S", merchantRefNo, req.getQuoteNo());	
				MotorIntegrationRequest integrationRequest =new MotorIntegrationRequest();
				integrationRequest.setBranchCode(req.getBranchCode());
				integrationRequest.setProductId(req.getProductId());
				integrationRequest.setQuoteNo(req.getQuoteNo());
				res =integration.integration(integrationRequest);
			}else {
				res.setMessage("FAILED");
				list.add(new ErrorList("101","CashPayment","Something went wrong in payment .Contact Admin...!"));
				res.setErrors(list);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public CommonResponse chequePayment(MadionPaymentRequest req) {
		CommonResponse res = new CommonResponse();
		try {
			List<ErrorList> list=validation.vaildatePaymenttDetail(req);
			if(CollectionUtils.isEmpty(list)) {
				String merchantRefNo=insertPaymentDetails(req);
				if(StringUtils.isNotBlank(merchantRefNo)) {
					paymentRepo.updateRequestPayStatus("S", merchantRefNo, req.getQuoteNo());
					MotorIntegrationRequest integrationRequest =new MotorIntegrationRequest();
					integrationRequest.setBranchCode(req.getBranchCode());
					integrationRequest.setProductId(req.getProductId());
					integrationRequest.setQuoteNo(req.getQuoteNo());
					res =integration.integration(integrationRequest);
				}else {
					res.setMessage("ERROR");
					list.add(new ErrorList("101","ChequePayment","Something went wrong in payment .Contact Admin...!"));
					res.setErrors(list);
				}
			}else {
				res.setErrors(list);
				res.setMessage("ERROR");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
}
