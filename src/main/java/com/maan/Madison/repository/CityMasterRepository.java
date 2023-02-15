/*
 * Java domain class for entity "CityMaster" 
 * Created on 2023-01-10 ( Date ISO 2023-01-10 - Time 16:17:04 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2023-01-10 ( 16:17:04 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.Madison.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.maan.Madison.entity.CityMaster;
import com.maan.Madison.entity.CityMasterId;
/**
 * <h2>CityMasterRepository</h2>
 *
 * createdAt : 2023-01-10 - Time 16:17:04
 * <p>
 * Description: "CityMaster" Repository
 */
 
 
 
public interface CityMasterRepository  extends JpaRepository<CityMaster,CityMasterId > , JpaSpecificationExecutor<CityMaster> {

	List<CityMaster> findByStatusIgnoreCase(String status);
	
	@Query(value="SELECT BRANCH_ID , BRANCH_NAME FROM BROKER_BRANCH_MASTER WHERE BRANCH_ID IN (SELECT DISTINCT(REGEXP_SUBSTR(LC_LOGIN,'[^,]+',1,LEVEL)) LC_LOGIN FROM ( SELECT SUB_BRANCH LC_LOGIN FROM LOGIN_MASTER WHERE LOGIN_ID=?1) B CONNECT BY LEVEL <= LENGTH(LC_LOGIN) - LENGTH(REPLACE(LC_LOGIN,',',''))+ 1 AND LC_LOGIN IS NOT NULL) AND STATUS='Y' AND MGEN_BRANCH_ID='01'",nativeQuery=true)
	List<Map<String,Object>> getBranchByLoginId(String loginId);

}