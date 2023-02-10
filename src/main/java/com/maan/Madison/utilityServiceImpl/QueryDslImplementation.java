package com.maan.Madison.utilityServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class QueryDslImplementation {
	
	
	@Autowired
	private static JPAQueryFactory jpaQueryFactory;
	

}
