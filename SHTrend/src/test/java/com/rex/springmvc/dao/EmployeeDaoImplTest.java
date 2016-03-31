package com.rex.springmvc.dao;

import java.math.BigDecimal;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.hibernate.criterion.Expression;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.rex.springmvc.model.Employee;
import com.rex.springmvc.resource.SpringMVCRest;
import com.rex.springmvc.server.EmbededWebServer;

public class EmployeeDaoImplTest extends EntityDaoImplTest {

	@Autowired
	EmployeeDao employeeDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Employee.xml"));
		return dataSet;
	}

	/*
	 * In case you need multiple datasets (mapping different tables) and you do
	 * prefer to keep them in separate XML's
	 * 
	 * @Override protected IDataSet getDataSet() throws Exception { IDataSet[]
	 * datasets = new IDataSet[] { new
	 * FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(
	 * "Employee.xml")), new
	 * FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(
	 * "Benefits.xml")), new
	 * FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(
	 * "Departements.xml")) }; return new CompositeDataSet(datasets); }
	 */

	@Test
	public void findById() {
		Assert.assertNotNull(employeeDao.findById(1));
		Assert.assertNull(employeeDao.findById(3));
	}

	@Test
	public void saveEmployee() {
		employeeDao.saveEmployee(getSampleEmployee());
		Assert.assertEquals(employeeDao.findAllEmployees().size(), 3);
	}

	@Test
	public void deleteEmployeeBySsn() {
		employeeDao.deleteEmployeeBySsn("11111");
		Assert.assertEquals(employeeDao.findAllEmployees().size(), 1);
	}

	@Test
	public void deleteEmployeeByInvalidSsn() {
		employeeDao.deleteEmployeeBySsn("23423");
		Assert.assertEquals(employeeDao.findAllEmployees().size(), 2);
	}

	@Test
	public void findAllEmployees() {
		Assert.assertEquals(employeeDao.findAllEmployees().size(), 2);
	}

	@Test
	public void findEmployeeBySsn() {
		Assert.assertNotNull(employeeDao.findEmployeeBySsn("11111"));
		Assert.assertNull(employeeDao.findEmployeeBySsn("14545"));
	}

	public Employee getSampleEmployee() {
		Employee employee = new Employee();
		employee.setName("Karen");
		employee.setSsn("12345");
		employee.setSalary(new BigDecimal(10980));
		employee.setJoiningDate(new LocalDate());
		return employee;
	}

	private MockMvc mockMvc;

//	EmbededWebServer server = new EmbededWebServer();

	@Autowired
	SpringMVCRest springMVCRest;

	@Autowired
	WebApplicationContext ctx;

	@BeforeTest
	public void setup() throws Exception {
		// SpringMVCRest springMVCRest = new SpringMVCRest();
		// mockMvc = MockMvcBuilders.standaloneSetup(springMVCRest).build();

		// server.startServer();
	}

	@AfterTest
	public void Destroyable() throws Exception {
		// server.stopServer();
	}

	@Test
	public void findEmpById() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/Rest/getEmployee/1","json").characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isFound())
				.andDo(MockMvcResultHandlers.print())
				.andReturn().getResponse().getContentAsString();

	}

}
