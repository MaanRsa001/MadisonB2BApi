package com.maan.Madison.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="PAYMENT_DETAIL")
public class Payment_Detail {

	@Id
	private Long quote_no;
	private Long product_id;
	private String payment_type;
	private Double premium;
	private String cheque_no;
	private String bank_name;
	private String micr_code;
	private Double cheque_amount;
	private Date cheque_date;
	private Date request_time;
	private Date response_time;
	private String response_tran_no;
	private String response_status;
	private String merchant_reference;
	private String customer_email;
	private String customer_name;
	private String application_no;
	private String response_message;
	private String card_number_masked;
	private String response_code;
	private String res_auth_trans_ref_no;
	private String res_payer_authentication;
	private String res_req_bill_to_surname;
	private String res_req_bill_to_address_city;
	private String res_req_card_expiry_date;
	private String res_req_postal_code;
	private String res_reason_code;
	private String res_auth_amount;
	private String res_auth_response;
	private String res_bill_trans_ref_no;
	private String res_req_bill_to_forename;
	private String res_req_payment_method;
	private String res_request_token;
	private String res_auth_time;
	private String res_req_amount;
	private String res_req_bill_to_email;
	private String res_payer_auth_reason_code;
	private String res_auth_avs_code_raw;
	private String res_transaction_id;
	private String res_req_currency;
	private String res_req_card_type;
	private String res_payer_auth_pares_status;
	private String res_decision;
	private String res_payer_auth_cavv;
	private String res_message;
	private String branch_code;
	private String bill_to_forename;
	private String bill_to_surname;
	private String bill_to_address_line1;
	private String bill_to_address_city;
	private String bill_to_address_country;
	private String bill_to_address_postal_code;
	private String bill_to_email;
	private String manual_login_id;
	private Date manual_update_time;
	private String manual_status;
	private String currency_type;
	private Long no_of_installment;
	private Double installment_amount;
	private String installment_frequency;
	private String installment_start_date;
	private String installment_yn;
	private String res_req_transaction_type;
	private String res_auth_code;
	private String res_req_recurring_frequency;
	private String res_req_no_of_installments;
	private String res_req_recurring_amount;
	private String res_payment_token;
	private String mobile_no;
	private String device_type;
	private Long installment_no;
	private String installment_remarks;
	private String risk_id;
	private String policy_no;
	private String renewal_yn;
	
	@Column(name = "MTN_MOBILE_NO")
	private String mtnMobileNo;
	@Column(name = "REFERENCE_NO")
	private String reference_no;
}
	