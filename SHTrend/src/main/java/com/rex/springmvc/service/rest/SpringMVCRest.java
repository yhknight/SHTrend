package com.rex.springmvc.service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rex.springmvc.model.Employee;
import com.rex.springmvc.service.EmployeeService;

@RestController
@RequestMapping("/Rest")
public class SpringMVCRest {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping("/getEmployee/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") int id) {
		Employee emp = employeeService.findById(id);
		HttpHeaders header = new HttpHeaders();
		header.set("MyResponse", "hello");
		// return ResponseEntity.created(location).header("MyResponseHeader",
		// "MyValue").body("Hello World");
		// return new ResponseEntity<Employee>(body, headers, statusCode)
		return new ResponseEntity<Employee>(emp, HttpStatus.FOUND);

	}
	
//	@RequestMapping("/getEmployee/{id}")
//	public Employee getEmployee(@PathVariable("id") int id) {
//		Employee emp = employeeService.findById(id);
//		HttpHeaders header = new HttpHeaders();
//		header.set("MyResponse", "hello");
//		// return ResponseEntity.created(location).header("MyResponseHeader",
//		// "MyValue").body("Hello World");
//		// return new ResponseEntity<Employee>(body, headers, statusCode)
//		return emp;
//
//	}
	
	

}
