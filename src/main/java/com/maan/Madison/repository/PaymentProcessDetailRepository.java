package com.maan.Madison.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maan.Madison.entity.PaymentProcessDetailId;
import com.maan.Madison.entity.PaymentProcessDetails;

@Repository
public interface PaymentProcessDetailRepository extends JpaRepository<PaymentProcessDetails, PaymentProcessDetailId> {

}
