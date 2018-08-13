package com.vnpt.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vnpt.demo.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    List<Employee> findByNameContaining(String q);

}
