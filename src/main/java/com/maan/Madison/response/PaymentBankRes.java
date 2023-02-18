package com.maan.Madison.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentBankRes {
	
	@JsonProperty("BankId")
	private String bankId;
	@JsonProperty("AccountName")
	private String accountName;
	@JsonProperty("AccountNo")
	private String accountNo;
	@JsonProperty("BankName")
	private String bankName;
	@JsonProperty("Branch")
	private String branch;
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("SwiftCode")
	private String swiftCode;

}
