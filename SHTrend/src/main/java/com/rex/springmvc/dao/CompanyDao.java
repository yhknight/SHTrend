package com.rex.springmvc.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rex.springmvc.model.Company;

public interface CompanyDao {

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	void saveCompany(Company company);

}
