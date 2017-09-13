package com.rex.springmvc.controller;

import java.nio.channels.FileChannel.MapMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.management.BadStringOperationException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rex.springmvc.dao.CompanyDao;
import com.rex.springmvc.dao.EmployeeDao;
import com.rex.springmvc.dao.StudentAndTeachDTO;
import com.rex.springmvc.model.Company;
import com.rex.springmvc.model.Employee;
import com.rex.springmvc.model.Teacher;
import com.rex.springmvc.service.EmployeeService;
import com.rex.springmvc.service.EmployeeServiceImpl;

@Controller
@RequestMapping("/")
public class AppController {

	// private final Object o1;
	@Autowired
	EmployeeService service;

	@Autowired
	private EmployeeDao dao;

	@Autowired
	private CompanyDao comp;
	/*
	 * @Autowired //如果要对类而非接口直接进行注入，必须要开启proxy-target-class="true"
	 * 或@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
	 * 
	 * @Qualifier("employeeService") EmployeeServiceImpl emp;
	 */

	@Autowired
	MessageSource messageSource;

	@Autowired
	ApplicationEventPublisher pub;

	// public AppController() {
	// o1=new String();
	// // TODO Auto-generated constructor stub
	// }
	/*
	 * This method will list all existing employees.
	 */
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listEmployees(ModelMap model, HttpSession session) {

		List<Employee> employees = service.findAllEmployees();
		model.addAttribute("employees", employees);
		// 模拟登陆成功后给session登陆成功标识赋值
		session.setAttribute("isLogin", true);
		return "allemployees";
	}

	/*
	 * This method will provide the medium to add a new employee.
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newEmployee(ModelMap model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		model.addAttribute("edit", false);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveEmployee(@Valid Employee employee, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*
		 * Preferred way to achieve uniqueness of field [ssn] should be
		 * implementing custom @Unique annotation and applying it on field [ssn]
		 * of Model class [Employee].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you
		 * can fill custom errors outside the validation framework as well while
		 * still using internationalized messages.
		 * 
		 */
		if (!service.isEmployeeSsnUnique(employee.getId(), employee.getSsn())) {
			FieldError ssnError = new FieldError("employee", "ssn", messageSource.getMessage("non.unique.ssn",
					new String[] { employee.getSsn() }, Locale.getDefault()));
			result.addError(ssnError);
			return "registration";
		}

		service.saveEmployee(employee);

		model.addAttribute("success", "Employee " + employee.getName() + " registered successfully");
		return "success";
	}

	/*
	 * This method will provide the medium to update an existing employee.
	 */
	@RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.GET)
	public String editEmployee(@PathVariable String ssn, ModelMap model) {
		Employee employee = service.findEmployeeBySsn(ssn);
		model.addAttribute("employee", employee);
		model.addAttribute("edit", true);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * updating employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.POST)
	public String updateEmployee(@Valid Employee employee, BindingResult result, ModelMap model,
			@PathVariable String ssn) {

		if (result.hasErrors()) {
			return "registration";
		}

		if (!service.isEmployeeSsnUnique(employee.getId(), employee.getSsn())) {
			FieldError ssnError = new FieldError("employee", "ssn", messageSource.getMessage("non.unique.ssn",
					new String[] { employee.getSsn() }, Locale.getDefault()));
			result.addError(ssnError);
			return "registration";
		}

		service.updateEmployee(employee);

		model.addAttribute("success", "Employee " + employee.getName() + " updated successfully");
		return "success";
	}

	/*
	 * This method will delete an employee by it's SSN value.
	 */
	@RequestMapping(value = { "/delete-{ssn}-employee" }, method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable String ssn) {
		service.deleteEmployeeBySsn(ssn);
		return "redirect:/list";
	}

	@RequestMapping(value = { "/pojo" }, method = RequestMethod.POST)
	@ResponseBody
	public Teacher HandlePojo(@RequestBody Teacher t) {
		return t;
	}

	@RequestMapping(value = { "/pojoa" }, method = RequestMethod.POST)
	@ResponseBody
	public List<Teacher> HandlePojoArray(@RequestBody Teacher[] ts) {

		return null;
	}

	@RequestMapping(value = { "/pojos" }, method = RequestMethod.POST)
	@ResponseBody
	public List<Teacher> HandlePojos(@RequestBody List<Teacher> ts) {

		return ts;
	}

	@RequestMapping(value = { "/pojov" }, method = RequestMethod.POST)
	@ResponseBody
	public List<Teacher> HandlePojoValidate(Teacher ts) {// if don`t add
															// @requestbody(for
															// json),it only
															// work by
															// form-urlencode

		return Arrays.asList(ts);
	}

	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	@Transactional // if outside function have @transactional then the method in
					// this method(with @Transactional(propagation =
					// Propagation.REQUIRED)) will be in the same transaction,in
					// this case
					// is 2 comp.saveCompany()will be in the same transaction
	// but if invoked methods with(@Transactional(propagation =
	// Propagation.REQUIRES_NEW)),then they will be in separate transaction
	public int doTest(@PathVariable Integer id) throws BadStringOperationException {
		// service.postCl(id);
		Company com1 = new Company();
		com1.setName("com1");
		comp.saveCompany(com1);

		Company com2 = new Company();
		com2.setName("com2");
		comp.saveCompany(com2);
		pub.publishEvent(Long.toString(com1.getId()) + "--" + Long.toString(com2.getId()));
		return id;
	}
}
