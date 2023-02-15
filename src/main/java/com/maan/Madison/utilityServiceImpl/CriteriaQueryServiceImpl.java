package com.maan.Madison.utilityServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.maan.Madison.controller.DocumentUploadController;
import com.maan.Madison.entity.DocumentMaster;
import com.maan.Madison.entity.DocumentUploadDetails;
import com.maan.Madison.entity.MotorDataDetail;
import com.maan.Madison.request.DocumentUploadGetReq;

@Component
public class CriteriaQueryServiceImpl {
	
	Logger log =LogManager.getLogger(DocumentUploadController.class);
	
	@PersistenceContext
	private EntityManager em;
	
	
	
	public List<Tuple> getDocumetUploadDetailsByQuoteNo(DocumentUploadGetReq req){
		List<Tuple> list = new ArrayList<Tuple>();
		try {
			CriteriaBuilder cb =em.getCriteriaBuilder();
			CriteriaQuery<Tuple> query =cb.createTupleQuery();
			Root<MotorDataDetail> mdd =query.from(MotorDataDetail.class);
			Root<DocumentUploadDetails> dud =query.from(DocumentUploadDetails.class);
			Root<DocumentMaster> dm =query.from(DocumentMaster.class);
			
			Predicate p1 =cb.equal(mdd.get("quoteNo"), dud.get("quoteNo"));
			Predicate p2 =cb.equal(mdd.get("vehicleId"), mdd.get("vtypeId"));
			Predicate p3 =cb.equal(dm.get("documentId"), dud.get("documentId"));
			Predicate p4 =cb.equal(dud.get("quoteNo"), req.getQuoteNo());
			Predicate p5 =cb.equal(dud.get("vtypeId"),req.getVtypeId());
			Predicate p6 =cb.equal(dud.get("productId"),req.getProductId());
			Predicate p7 =cb.notEqual(dm.get("userType"), "surveyor");
			
			query.multiselect(mdd.get("engineNumber").alias("engineNumber"),mdd.get("registrationNo").alias("registrationNo"),
					mdd.get("chassisNo").alias("chassisNo"),dud.get("documentId").alias("documentId"),dud.get("documentDesc").alias("documentDesc")
					,dm.get("documentDesc").alias("documentDesc"),dud.get("filePathName").alias("filePathName"),dud.get("fileName").alias("fileName"),
					dud.get("description").alias("description")
					);
			query.where(p1,p2,p3,p4,p5,p6,p7);
			
			list=em.createQuery(query).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return list;
	}

}
