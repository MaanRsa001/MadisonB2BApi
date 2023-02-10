package com.maan.Madison.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maan.Madison.entity.Payment_Detail;


@Repository
public interface PaymentRepository extends JpaRepository<Payment_Detail, Long> {
	
	@Query(value="SELECT NVL(PM.RSACODE,0) FROM PRODUCT_MASTER PM,HOME_POSITION_MASTER HPM WHERE HPM.QUOTE_NO=?1 AND PM.STATUS='Y' AND PM.BRANCH_CODE=?2 AND PM.PRODUCT_ID=NVL(HPM.PROPOSAL_NO,HPM.PRODUCT_ID)",nativeQuery=true)
	String get_rsacode(String quoteno,String branchcode);
	
	@Query(value="SELECT EXTRACT(YEAR FROM TRN.CLO_DATE_CLOSED) AS CLOSE_YEARS,EXTRACT(MONTH FROM TRN.CLO_DATE_CLOSED) AS CLOSE_MONTHS,EXTRACT(DAY FROM TRN.CLO_DATE_CLOSED) AS CLOSE_DAYS,EXTRACT(YEAR FROM TRN.CLO_DATE_OPENED) AS OPEN_YEARS,EXTRACT(MONTH FROM TRN.CLO_DATE_OPENED) AS OPEN_MONTHS,EXTRACT(DAY FROM TRN.CLO_DATE_OPENED) AS OPEN_DAYS FROM T_TRN_CLOSING TRN,PRODUCT_MASTER PM,HOME_POSITION_MASTER HPM WHERE PM.BRANCH_CODE=TRN.BRANCH_CODE AND TRN.BRANCH_CODE=?1 AND PM.PRODUCT_ID=NVL(HPM.PROPOSAL_NO,HPM.PRODUCT_ID) AND  PM.RSACODE=TRN.PRODUCT_CORE_CODE AND PM.RSACODE=?2 AND HPM.QUOTE_NO=?3 AND PM.AMEND_ID=(SELECT MAX(AMEND_ID) FROM PRODUCT_MASTER WHERE PRODUCT_ID=PM.PRODUCT_ID AND BRANCH_CODE = PM.BRANCH_CODE )",nativeQuery=true)
	List<Map<String,Object>> get_close_trnrsacode(String branchcode,String rsacode,String quoteno);
	
	@Query(value="SELECT EXTRACT(YEAR AS TRN.CLO_DATE_CLOSED) AS CLOSE_YEARS,EXTRACT(MONTH AS TRN.CLO_DATE_CLOSED) AS CLOSE_MONTHS,EXTRACT(DAY AS TRN.CLO_DATE_CLOSED) AS CLOSE_DAYS,EXTRACT(YEAR AS TRN.CLO_DATE_OPENED) AS OPEN_YEARS,EXTRACT(MONTH AS TRN.CLO_DATE_OPENED) AS OPEN_MONTHS,EXTRACT(DAY ASTRN.CLO_DATE_OPENED) AS OPEN_DAYS FROM T_TRN_CLOSING TRN, PRODUCT_MASTER PM, HOME_POSITION_MASTER HPM WHERE PM.BRANCH_CODE = TRN.BRANCH_CODE AND TRN.BRANCH_CODE = ?1 AND PM.PRODUCT_ID = NVL (HPM.PROPOSAL_NO, HPM.PRODUCT_ID) AND PM.RSACODE = TRN.PRODUCT_CORE_CODE AND HPM.QUOTE_NO=?2 AND PM.AMEND_ID=(SELECT MAX(AMEND_ID) FROM PRODUCT_MASTER WHERE PRODUCT_ID = PM.PRODUCT_ID AND EFFECTIVE_DATE = PM.EFFECTIVE_DATE ) AND (PM.EFFECTIVE_DATE) <= (SYSDATE) AND PM.STATUS='Y'",nativeQuery=true)
	List<Map<String,Object>> get_close_trn(String branchcode,String quoteno);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE HOME_POSITION_MASTER SET DEBIT_NOTE_DATE=TO_TIMESTAMP(?1,'DD/MM/YYYY HH:MI:SS'),EFFECTIVE_DATE=TO_TIMESTAMP(?2,'DD/MM/YYYY HH:MI:SS')  WHERE  QUOTE_NO=?3",nativeQuery=true)
	int upd_hpmdate_quoteno(Object debitdate,Object effdate,String quoteno);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE HOME_POSITION_MASTER SET DEBIT_NOTE_DATE=TO_TIMESTAMP(?1,'DD/MM/YYYY HH:MI:SS'),EFFECTIVE_DATE=TO_TIMESTAMP(?2,'DD/MM/YYYY HH:MI:SS')  WHERE  POLICY_NO=?3",nativeQuery=true)
	int upd_hpmdate_policyno(Object debitdate,Object effdate,String policyno);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE HOME_POSITION_MASTER SET STATUS = ?1 WHERE QUOTE_NO=?2",nativeQuery=true)
	int upd_payment_process_details(String status,String quoteno);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE MOTOR_DATA_DETAIL MDD SET POLICY_NO=?1||'-'||VEHICLE_ID,STATUS='P' WHERE QUOTE_NO=?2",nativeQuery=true)
	int upd_policy_no_vehicle_id(String policyno,String quoteno);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE PAYMENT_PROCESS_DETAIL SET TYPE='CC',STATUS='Y' WHERE QUOTE_NO=?1",nativeQuery=true)
	int getUpdPaymentProcessStaus(String quoteno);

	@Query(value="SELECT PAYMENT_TRAN_ID.NEXTVAL FROM DUAL",nativeQuery=true)
	String getMerchantRefNo();

	@Query(value="SELECT TO_CHAR(PREMIUM_DATE,'DD/MM/YYYY') PREMIUM_DATE_VAL,TO_CHAR(PREMIUM_DATE,'YYYYMMDD') PAYMENT_PREMIUM_DATE,ID1.* FROM  installment_details ID1 WHERE QUOTE_NO=?1 ORDER BY INSTALLMENT_NO ASC",nativeQuery=true)
	List<Map<String, Object>> getInstallmentDet(String quoteNo);
	
	@Query(value="SELECT QUOTE_NO, PRODUCT_ID, PREMIUM, MERCHANT_REFERENCE, CURRENCY_TYPE, REFERENCE_NO, MOBILE_NO,MTN_MOBILE_NO FROM PAYMENT_DETAIL WHERE MERCHANT_REFERENCE =?1",nativeQuery=true)
	List<Map<String, Object>> getPaymentDetByMerchantRefNo(String merchantRefNo);

	@Modifying
	@Transactional
	@Query(value="update home_position_master set payment_status =?1,payment_id=?2 where quote_no=?3 and PRODUCT_ID=?4",nativeQuery=true)
	int updatePaymentStatus(String payment_status, String merchantRefNo, String quote_no, String product_id);

	@Query(value="SELECT JWT_TOKEN FROM JWT_TOKEN WHERE TOKEN_FOR = 'MTN_PAYMENT' AND SYSDATE BETWEEN ENTRY_DATE AND EXPIRY_DATE ORDER BY ENTRY_DATE DESC",nativeQuery=true)
	String getJwtToken();

	@Modifying
	@Transactional
	@Query(value="INSERT INTO api_call_history( transaction_id, api_description, request_method, REQUEST_URL, request_headers, request_string, response_status, response_string, error_response, entry_date) VALUES ( ?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9,sysdate )",nativeQuery=true)
	int  insertApiDetails(String merchantRefId, String apiDesc, String method, String api, String headers, String request, String responseStatus, String response, String errorResponse);

	@Query(value ="select * from HOME_POSITION_MASTER where payment_status='P'",nativeQuery=true)
	List<Map<String, Object>> getPendingPaymentDetByStatus();

	@Query(value="select * from PAYMENT_DETAIL where MERCHANT_REFERENCE =?1",nativeQuery=true)
	Map<String, Object> getPendingPayDet(String merchatRefNo);

	@Modifying
	@Transactional
	@Query(value="UPDATE HOME_POSITION_MASTER SET PAYMENT_STATUS=?1,PAYMENT_ID=?2 WHERE QUOTE_NO=?3",nativeQuery=true)
	int updateRequestPayStatus(String paymentStatus, String merchantRefNo, String quoteNo);

	@Modifying
	@Transactional
	@Query(value="INSERT INTO jwt_token(token_for, token_type, jwt_token, expires_second, entry_date, expiry_date) VALUES ( ?1, ?2, ?3, ?4 , sysdate, (SELECT sysdate + interval ?4 second FROM dual) )",nativeQuery=true)
	int insert_jwt_token(String string, String string2, String response, String expires_in);	
	
	@Query(value="select quote_no,product_id from payment_detail where MERCHANT_REFERENCE=?1",nativeQuery=true)
	Map<String,Object> getQuoteNoByMerchantRefNo(String merchant_ref_no);
	
	@Query(value="SELECT JWT_TOKEN FROM JWT_TOKEN WHERE TOKEN_FOR = 'AIRTEL_PAYMENT' AND SYSDATE BETWEEN ENTRY_DATE AND EXPIRY_DATE ORDER BY ENTRY_DATE DESC",nativeQuery=true)
	String getAirtelJwtToken();

	@Query(value="SELECT * FROM PAYMENT_MASTER WHERE CURRENCY_TYPE=?",nativeQuery=true)
	Map<String,Object> getPaymentMasterByCurrencyType(String currencyType);
	
	@Query(value ="SELECT E.PRODUCT_ID,E.PRODUCT_NAME,F.QUOTE_NO, F.PROPOSAL_NO,F.CUSTOMER_ID,F.LOGIN_ID,F.PRODUCT_ID,F.COMPANY_ID,F.POLICY_NO,F.POLICY_TERM,F.PREMIUM,F.AMEND_ID,TO_CHAR(F.INCEPTION_DATE,'DD/MM/YYYY') INCEPTION_DATE,TO_CHAR(F.EXPIRY_DATE,'DD/MM/YYYY') EXPIRY_DATE, TO_CHAR(F.EFFECTIVE_DATE,'DD/MM/YYYY') EFFECTIVE_DATE, TO_CHAR(F.ENTRY_DATE,'DD/MM/YYYY') ENTRY_DATE,F.REMARKS,F.STATUS,F.APPLICATION_NO,F.DEBIT_NOTE_NO,F.DEBIT_NOTE_DATE,F.LAPSED_REMARKS,F.LAPSED_DATE,F.LAPSED_UPDATED_BY,F.PDF_BROKER_STATUS,F.BROKER_ADDITIONAL_COMMISSION,F.NO_CLAIM_DISCOUNT,F.NO_CLAIM_DISCOUNT_VALUE,F.EXCESS_PREMIUM,F.ADMIN_REMARKS,F.ADMIN_REFERRAL_STATUS,F.EXCESS_SIGN,F.OVERALL_PREMIUM,F.COMMISSION,F.SUMMARY_CLAUSES,F.SUMMARY_REMARKS,F.REFERRAL_DESCRIPTION,F.ADMIN_SUMMARY_STATUS,F.BED_ROOM,F.AGE_ABOVE_SIXTY_FIVE,F.EXISTING_MEDICAL_CONDITION,F.MEDICAL_TRAVEL_CLAIMS,F.CANCELLED_REASON,F.CANCELLED_DATE,F.CANCELLED_BY,F.REISSUED_POLICY_NO,F.REISSUED_QUOTE_NO,F.AIRMILES_NO,F.INSURANCE_DETAILS,F.RECEIPT_NO,F.RECEIPT_DATE,F.PAYMENT_MODE,F.SCHEME_ID,F.CONTENT_TYPE_ID,F.BTOC,F.DECLARATION_STATUS,F.PAYMENT_STATUS,F.POLICY_DATE,F.APPROVED_BY,F.CSH_ID_TYP_CODE,F.FLEET_INT_STATUS,F.FLEET_NO,F.INGSERIAL_NUMBER,F.DISCOUNT_PREMIUM,F.COMMISSION_PERCENTAGE,F.CREDIT_NO,F.CREDIT_DATE,F.COVER_NOTE_NUMBER,F.APPLICATION_ID,F.POLICY_FEE,F.CLASS_ID,F.BRANCH_CODE,F.ENDT_TYPE_ID,F.ORIGINAL_POLICY_NO,F.ENDT_PREMIUM,F.ENDT_COMMISSION,F.CHQ_INV_NO,F.BROKER_CODE,F.AC_EXECUTIVE_ID,F.TRAN_ID,F.ENDT_STATUS,F.QUATER_ID,F.VEHICLE_ID,F.CURRENCY,F.SMS_YN,F.MIN_PREMIUM_YN,F.RENEWAL_STATUS,F.RENEWAL_OLD_POLICY,F.RENEWAL_DATE_YN,F.INSTALLMENT_YN,F.ONLINE_DISCOUNT,F.POLICY_FEE_PERCENT,F.BROKER_BRANCH,F.ACTUAL_PREMIUM,F.ACTUAL_OPPREMIUM,F.VOLUME_DISCOUNT_AMOUNT,F.CORPORATE_DISCOUNT_AMOUNT,F.SPECIAL_DISCOUNT_AMOUNT,(PI.TITLE||' . '||PI.FIRST_NAME||' '||PI.LAST_NAME) CUST_NAME,PI.TITLE,PI.FIRST_NAME,PI.LAST_NAME,PI.NATIONALITY, TO_CHAR(PI.DOB,'DD/MM/YYYY') DOB,PI.GENDER, PI.TELEPHONE,PI.MOBILE,PI.FAX,PI.EMAIL,PI.ADDRESS1,PI.ADDRESS2,PI.OCCUPATION,PI.POBOX,PI.COUNTRY, PI.EMIRATE,PI.AGENCY_CODE,PI.OA_CODE,PI.COMPANY_NAME,PI.MISSIPPI_CUSTOMER_CODE, PI.CITY,PI.FREIGHT_FORWARD_USER,PI.CUSTOMER_LOGIN_ID,PI.CUSTOMER_SOURCE,PI.FD_CODE,PI.CLIENT_CUSTOMER_ID, PI.CUST_AR_NO,PI.STATE,PI.PASSPORT_NUMBER,PI.NRC,PI.ALTERNATE_MOBILE,PI.CUSTOMER_TYPE, PI.COMPANY_REG_NO,PI.CUST_NAME_ARABIC,PI.DOB_AR FROM PRODUCT_MASTER E, HOME_POSITION_MASTER F, PERSONAL_INFO PI WHERE F.QUOTE_NO=?1 AND F.PRODUCT_ID=?2 AND F.BRANCH_CODE=?3 AND PI.CUSTOMER_ID = F.CUSTOMER_ID AND F.PRODUCT_ID=E.PRODUCT_ID",nativeQuery=true)
	Map<String,Object> getPaymentInsertDetails(String quoteNo,String productId,String branchCode);
	
	@Query(value="select FIRST_NAME,LAST_NAME,ADDRESS1,EMIRATE,CITY,POBOX,EMAIL FROM PERSONAL_INFO WHERE CUSTOMER_ID=(SELECT CUSTOMER_ID FROM HOME_POSITION_MASTER WHERE QUOTE_NO=?1)",nativeQuery=true)
	Map<String,Object> getPersonalDetailsByQuoteNo(String quoteNo);

	@Modifying
	@Transactional
	@Query(value="INSERT INTO jwt_token(token_for, token_type, jwt_token, expires_second, entry_date, expiry_date) VALUES ( ?1, ?2, ?3, ?4 , sysdate, (SELECT sysdate + interval '3600' second FROM dual) )",nativeQuery=true)
	int insert_mtn_token(String tokenfor, String tokentype, String token, String expires_in);

	@Query(value="SELECT JWT_TOKEN FROM JWT_TOKEN WHERE TOKEN_FOR = 'MTN_PAYMENT' AND SYSDATE BETWEEN ENTRY_DATE AND EXPIRY_DATE ORDER BY ENTRY_DATE DESC",nativeQuery=true)
	String getMtnJwtToken();
	
	
	
}
