package com.vnpt.demo.service;

import java.util.List;

import com.vnpt.demo.model.Employee;

public interface EmployeeService {
	Iterable<Employee> findAll();

    List<Employee> search(String q);

    Employee findOne(int id);

    void save(Employee contact);

    void delete(int id);
}
