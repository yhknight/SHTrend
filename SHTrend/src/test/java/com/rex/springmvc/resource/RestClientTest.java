package com.rex.springmvc.resource;

import java.math.BigDecimal;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.rex.springmvc.configuration.HibernateTestConfiguration;
import com.rex.springmvc.dao.EmployeeDao;
import com.rex.springmvc.model.Employee;
import com.rex.springmvc.server.EmbededWebServer;

@ContextConfiguration(classes = { HibernateTestConfiguration.class })
@WebAppConfiguration()
public class RestClientTest extends AbstractTransactionalTestNGSpringContextTests {

	EmbededWebServer ems = new EmbededWebServer();
	RestTemplate tmp = new RestTemplate();
	String baseURI = "http://localhost:8081/Rest";

	@Autowired
	EmployeeDao employeeDao;

	@BeforeTest
	public void startServer() throws Exception {
		ems.startServer();
	}

	@Test
	//@Rollback(false)
	public void prepareData() {
		Employee emp = new Employee();
		emp.setName("TOM");
		emp.setSsn("12345");
		emp.setSalary(new BigDecimal(10980));
		emp.setJoiningDate(new LocalDate());
		employeeDao.saveEmployee(emp);
	}

	//
	@AfterTest
	public void stopServer() throws Exception {
		ems.stopServer();

	}

	@Test
	public void testFindEmployeeById() {
		ResponseEntity<Employee> entity = tmp.getForEntity(baseURI + "/getEmployee1/{id}", Employee.class, 1);
		Assert.assertEquals(entity.getStatusCode(), HttpStatus.OK);
		// Assert.assertEquals(entity.getHeaders().getContentType(),
		// MediaType.APPLICATION_JSON);
		//Assert.assertEquals(entity.getBody().getName().toUpperCase(), "TOM");

	}

}
