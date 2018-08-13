package com.vnpt.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vnpt.demo.model.MyEntity;
import com.vnpt.demo.repository.CountryRepository;
import com.vnpt.demo.repository.MyEntityRepository;

@Controller
public class MyEntityController {
	@Autowired
	private MyEntityRepository myEntityRepository;
	@Autowired
	private CountryRepository countryRepository; 
	@GetMapping("/employee/{id}/update")
	public String updateEmployee(@PathVariable int id,@RequestParam("name") String name, @RequestParam("phone") String phone, Model model) throws Exception {
		model.addAttribute("employee", myEntityRepository.updateEmployee(id, name, phone));
		return "form";
	}
	@GetMapping("/employee/test")
	@ResponseBody
	public Object employeeTest(@RequestParam(value="text") String text, Model model) {
		Object result = null;
		result = myEntityRepository.getAllEmployees();
//		result = myEntityRepository.callFunctionSingleValue(text);
//		result = myEntityRepository.callFunctionRefCursorValue(Integer.parseInt(text));
//		result = myEntityRepository.callStoredProcedureSingleOutputValue(text);
//		result = myEntityRepository.callStoredProcedureRefCursorOutputValue(text);
		return result;
	}
	@GetMapping("/employee/new")
	@ResponseBody
	public MyEntity newEmployee(@RequestParam("name") String name,@RequestParam("phone") String phone, Model model) throws Exception {
		MyEntity result = myEntityRepository.createEmployee(name, phone);
		return result;
	}
	@ResponseBody
	@GetMapping("/employee/{id}/remove")
	public boolean removeEmployee(@PathVariable int id, Model model) throws Exception {
		boolean result = myEntityRepository.removeEmployee(id);
		return result;
	}
	@ResponseBody
	@GetMapping("/employee/{id}/read")
	public MyEntity readEmployee(@PathVariable int id, Model model) throws Exception {
		countryRepository.findById(id);
		return myEntityRepository.readEmployee(id);
	}
}
