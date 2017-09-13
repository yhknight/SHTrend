package com.rex.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.rex.springmvc.model.Company;
import com.rex.springmvc.model.Employee;

@Repository("companyDao")
public class CompanyDaoImpl extends AbstractDao<Integer, Company>implements CompanyDao {

	@Override
	public void saveCompany(Company company) {
		persist(company);

	}

}
