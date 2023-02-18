package com.maan.Madison.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@Entity
@Table(name = "PAYMENT_PROCESS_DETAIL")
@Builder
@IdClass(PaymentProcessDetailId.class)
public class PaymentProcessDetails {
	
	@Id
	@Column(name = "POLICY_NO")
	private String policyNo;
	
	@Id
	@Column(name = "QUOTE_NO")
	private String quoteNo;
	
	@Column(name = "TYPE")
	private String type;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "RESPONSE_TIME")
	private Date responseTime;
	@Column(name = "REMARKS")
	private String remarks;
	@Column(name = "LOGIN_ID")
	private String loginId;
	@Column(name = "ALLOCATED_PERSON")
	private String allocatePerson;
	@Column(name = "SNO")
	private Long sno;
	

	


}
