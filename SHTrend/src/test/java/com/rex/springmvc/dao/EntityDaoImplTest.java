package com.rex.springmvc.dao;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeMethod;

import com.rex.springmvc.configuration.HibernateTestConfiguration;

@ContextConfiguration(classes = { HibernateTestConfiguration.class/*, AppConfig.class*/ })
@WebAppConfiguration(/*value = "src/java/webapp"*/)
public abstract class EntityDaoImplTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	DataSource dataSource;

	@BeforeMethod 
	public void setUp() throws Exception {
		IDatabaseConnection dbConn = new DatabaseDataSourceConnection(dataSource);
		DatabaseOperation.CLEAN_INSERT.execute(dbConn, getDataSet());
	}

	protected abstract IDataSet getDataSet() throws Exception;

}