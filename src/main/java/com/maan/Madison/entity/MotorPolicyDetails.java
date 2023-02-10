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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MOTOR_POLICY_DETAILS")
@Entity
public class MotorPolicyDetails {
	
	@Id
	@Column(name = "QUOTENO")
	private Long quoteNo;
	
	@Column(name = "APPLICATIONNO")
	private Long applicationNo;
	@Column(name = "POLICYTYPE")
	private Long policyType;
	@Column(name = "ISSELECTCOVER")
	private Long isSelectedCover;
	@Column(name = "PREMIUM")
	private Double premium;
	@Column(name = "OPTIONAL_COVER")
	private String optionalCover;
	@Column(name = "POLICYSTARTDATE")
	private Date policyStartDate;
	@Column(name = "POLICYENDDATE")
	private Date policyEndDate;
	@Column(name = "REFERRAL_REMARKS")
	private String referalRemarks;
	@Column(name = "CURRENCY_TYPE")
	private String currencyType;

}
