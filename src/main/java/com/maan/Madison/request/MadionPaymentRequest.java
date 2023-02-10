package com.maan.Madison.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MadionPaymentRequest {
	
	@JsonProperty("QuoteNo")
	private String quoteNo;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("CashDepositBank")
	private String cashDepositBank;
	@JsonProperty("CashAmount")
	private String cashAmount;
	@JsonProperty("CashChellanNo")
	private String cashChellanNo;
	@JsonProperty("CashInstrumentDate")
	private String cashInstrumentDate;
	@JsonProperty("ChequeDate")
	private String chequeDate;
	@JsonProperty("ChequeAmount")
	private String chequeAmount;
	@JsonProperty("BankName")
	private String bankName;
	@JsonProperty("MicrCode")
	private String micrCode;
	@JsonProperty("MtnMobileNo")
	private String mtnMobileNo;
	@JsonProperty("InstallmentYN")
	private String installmentYN;
	@JsonProperty("ChequeNo")
	private String chequeNo;
}
	
	