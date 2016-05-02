package com.rex.springmvc.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rex.springmvc.dao.StudentAndTeachDTO;
import com.rex.springmvc.model.Employee;
import com.rex.springmvc.service.EmployeeService;

@RestController
@RequestMapping("/Rest")
public class SpringMVCRest {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping("/getEmployee/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") int id) {
		System.out.println("xxxxxxxxxxxxxxxx");
		Employee emp = employeeService.findById(id);
		HttpHeaders header = new HttpHeaders();
		header.set("MyResponse", "hello");
		// return ResponseEntity.created(location).header("MyResponseHeader",
		// "MyValue").body("Hello World");
		// return new ResponseEntity<Employee>(body, headers, statusCode)
		return new ResponseEntity<Employee>(emp, HttpStatus.FOUND);

	}
	
	@RequestMapping("/getEmployee1/{id}")
	public Employee getEmployee1(@PathVariable("id") int id) {
		Employee emp = employeeService.findById(id);
		HttpHeaders header = new HttpHeaders();
		header.set("MyResponse", "hello");
		// return ResponseEntity.created(location).header("MyResponseHeader",
		// "MyValue").body("Hello World");
		// return new ResponseEntity<Employee>(body, headers, statusCode)
		return emp;

	}
	
	@RequestMapping("/getAll")
	public List<Employee> getAll() {
//		Employee emp = ;
//		HttpHeaders header = new HttpHeaders();
//		header.set("MyResponse", "hello");
		// return ResponseEntity.created(location).header("MyResponseHeader",
		// "MyValue").body("Hello World");
		// return new ResponseEntity<Employee>(body, headers, statusCode)
		return employeeService.findAllEmployees();

	}
	
	@RequestMapping(value = { "/getST/{id}" }, method = RequestMethod.GET)
	public List<StudentAndTeachDTO> getSt(@PathVariable int id) {
		List<StudentAndTeachDTO> st = employeeService.getStudentAndTeacher(id);
		return st;
	}
	
	@RequestMapping(value = {"/exceptionTest"},method = RequestMethod.GET)
	public String ExceptionTestCase() throws Exception{
		
		throw new Exception("this is a runtime exception!!");
		//return "exception test";
		
	}

	
	

}
