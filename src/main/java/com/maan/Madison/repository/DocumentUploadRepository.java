package com.maan.Madison.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maan.Madison.entity.DocumentUploadDetails;
import com.maan.Madison.entity.DocumentUploadDetailsId;

@Repository
public interface DocumentUploadRepository  extends JpaRepository<DocumentUploadDetails, DocumentUploadDetailsId>{

}
