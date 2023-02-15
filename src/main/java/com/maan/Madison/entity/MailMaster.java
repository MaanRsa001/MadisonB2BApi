/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2023-02-13 ( Date ISO 2023-02-13 - Time 15:54:50 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2023-02-13 ( 15:54:50 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.Madison.entity;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Table;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;




/**
* Domain class for entity "MailMaster"
*
* @author Telosys Tools Generator
*
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Builder
@IdClass(MailMasterId.class)
@Table(name="MAIL_MASTER")


public class MailMaster implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="SMTP_USER", nullable=false, length=100)
    private String     smtpUser ;

    @Id
    @Column(name="APP_ID", nullable=false, length=50)
    private String     appId ;

    @Id
    @Column(name="STATUS", nullable=false, length=50)
    private String     status ;

    @Id
    @Column(name="COMPANY_NAME", nullable=false, length=300)
    private String     companyName ;

    //--- ENTITY DATA FIELDS 
    @Column(name="MAIL_CC", length=100)
    private String     mailCc ;

    @Column(name="SMTP_HOST", length=100)
    private String     smtpHost ;

    @Column(name="SMTP_PWD", length=100)
    private String     smtpPwd ;

    @Column(name="EXP_DATE", length=100)
    private String     expDate ;

    @Column(name="EXP_TIME", length=100)
    private String     expTime ;

    @Column(name="PWD_CNT")
    private BigDecimal pwdCnt ;

    @Column(name="PWD_LEN")
    private BigDecimal pwdLen ;

    @Column(name="HOME_APP", length=500)
    private String     homeApp ;

    @Column(name="ADDRESS", length=100)
    private String     address ;

    @Column(name="REMARKS", length=100)
    private String     remarks ;

    @Column(name="TO_ADDRESS", length=100)
    private String     toAddress ;

    @Column(name="SMTP_PORT", nullable=false)
    private BigDecimal smtpPort ;

    @Column(name="AUTHORIZ_YN", length=1)
    private String     authorizYn ;

    @Column(name="INS_COMPANY_ID")
    private BigDecimal insCompanyId ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFFECTIVE_DATE")
    private Date       effectiveDate ;

    @Column(name="AMENDID")
    private BigDecimal amendid ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ENTRYDATE")
    private Date       entrydate ;

    @Column(name="HOME_APPLICATION_ID", length=20)
    private String     homeApplicationId ;

    @Column(name="APPLICATION_ID", length=20)
    private String     applicationId ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}



