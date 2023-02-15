package com.maan.Madison.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DocumentUploadDetailsId implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long quoteNo;
	
	private Long documentId ;
    
    private Long     productId ;
    
    private Long vtypeId;

}
