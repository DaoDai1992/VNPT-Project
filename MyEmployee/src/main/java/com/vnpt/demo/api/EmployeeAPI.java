package com.vnpt.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vnpt.demo.model.Employee;
import com.vnpt.demo.service.EmployeeService;


@RestController
public class EmployeeAPI {
	@Autowired
	private EmployeeService employeeService;

	@PreAuthorize("hasRole('API')")
	@GetMapping("/api/employee")
	public ResponseEntity<Iterable<Employee>> index() {
		Iterable<Employee> employee = employeeService.findAll();
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	

}
