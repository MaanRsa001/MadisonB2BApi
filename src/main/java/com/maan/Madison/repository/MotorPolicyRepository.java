package com.maan.Madison.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maan.Madison.entity.MotorPolicyDetails;

@Repository
public interface MotorPolicyRepository  extends JpaRepository<MotorPolicyDetails, Long>{

}
