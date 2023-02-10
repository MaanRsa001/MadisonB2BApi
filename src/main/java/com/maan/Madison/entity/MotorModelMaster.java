/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2023-01-10 ( Date ISO 2023-01-10 - Time 16:19:51 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2023-01-10 ( 16:19:51 )
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
* Domain class for entity "MotorModelMaster"
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
@IdClass(MotorModelMasterId.class)
@Table(name="MOTOR_MODEL_MASTER")


public class MotorModelMaster implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="MODEL_ID", nullable=false)
    private BigDecimal modelId ;

    @Id
    @Column(name="MAKE_ID", nullable=false)
    private BigDecimal makeId ;

    @Id
    @Column(name="AMEND_ID", nullable=false)
    private BigDecimal amendId ;

    //--- ENTITY DATA FIELDS 
    @Column(name="MODEL_NAME", length=50)
    private String     modelName ;

    @Column(name="STATUS", length=1)
    private String     status ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFFECTIVE_DATE")
    private Date       effectiveDate ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIRY_DATE")
    private Date       expiryDate ;

    @Column(name="REMARKS", length=100)
    private String     remarks ;

    @Column(name="BRANCH_CODE", nullable=false, length=8)
    private String     branchCode ;

    @Column(name="CORE_CODE", length=10)
    private String     coreCode ;

    @Column(name="MODEL_NAME_ARABIC", length=200)
    private String     modelNameArabic ;

    @Column(name="REFERRAL_STATUS", length=1)
    private String     referralStatus ;

    @Column(name="CATEGORY_ID")
    private BigDecimal categoryId ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}


