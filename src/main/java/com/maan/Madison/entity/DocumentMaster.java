/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2023-02-13 ( Date ISO 2023-02-13 - Time 15:53:30 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2023-02-13 ( 15:53:30 )
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
import javax.persistence.*;




/**
* Domain class for entity "DocumentMaster"
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
@IdClass(DocumentMasterId.class)
@Table(name="DOCUMENT_MASTER")


public class DocumentMaster implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="DOCUMENT_ID", nullable=false)
    private BigDecimal documentId ;

    @Id
    @Column(name="PRODUCT_ID", nullable=false, length=100)
    private String     productId ;

    //--- ENTITY DATA FIELDS 
    @Column(name="DOCUMENT_DESC", length=100)
    private String     documentDesc ;

    @Column(name="POLICY_TYPE", length=20)
    private String     policyType ;

    @Column(name="MANDATORY_STATUS", length=1)
    private String     mandatoryStatus ;

    @Column(name="STATUS", length=1)
    private String     status ;

    @Column(name="REMARKS", length=100)
    private String     remarks ;

    @Column(name="DISPLAY_ORDER")
    private BigDecimal displayOrder ;

    @Column(name="DOC_APPLICABLE", length=20)
    private String     docApplicable ;

    @Column(name="USER_TYPE", length=20)
    private String     userType ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}


