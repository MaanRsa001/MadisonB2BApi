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
@IdClass(DocumentUploadDetailsId.class)
@Table(name = "DOCUMENT_UPLOAD_DETAILS")
@Builder
public class DocumentUploadDetails {
	
	@Id
	@Column(name = "QUOTE_NO")
	private Long quoteNo;
	
	@Id
	@Column(name = "DOC_TYPE_ID")
	private Long documentId;
	
	@Id
	@Column(name = "PRODUCT_ID")
	private Long productId;
	
	@Id
	@Column(name = "VTYPE_ID")
	private Long vtypeId;
	
	@Column(name = "FILE_PATH_NAME")
	private String filePathName;
	@Column(name = "UPLOADED_TIME")
	private Date uploadTime;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "FILE_NAME")
	private String fileName;
	
	
	

}
