package com.vnpt.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vnpt.demo.model.AppRoleData;
import com.vnpt.demo.model.Employee;
import com.vnpt.demo.service.EmployeeService;
import com.vnpt.demo.service.RestrictDataService;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private RestrictDataService restrictDataService;
	@GetMapping("/employee")
	public String index(Model model) {
		String result = "list";
		try {
			Iterable<Employee> iterable = employeeService.findAll();
			if(iterable!=null) {
				List<Employee> employees = restrictDataService.restrictIterator(iterable.iterator(), AppRoleData.READ);
				model.addAttribute("employees", employees);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	@GetMapping("/employee/aa")
	public String index3(Model model) {
		model.addAttribute("employees", employeeService.findAll());
		return "list";
	}


	@GetMapping("/employee/create")
	public String create(Model model) {
		model.addAttribute("employee", new Employee());
		return "form";
	}

	@GetMapping("/employee/{id}/edit")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("employee", employeeService.findOne(id));
		return "form";
	}

	@PostMapping("/employee/save")
	public String save(@Valid Employee employee, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "form";
		}
		employeeService.save(employee);
		redirect.addFlashAttribute("success", "Saved employee successfully!");
		return "redirect:/employee";
	}

	@GetMapping("/employee/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		employeeService.delete(id);
		redirect.addFlashAttribute("success", "Deleted employee successfully!");
		return "redirect:/employee";
	}

	@GetMapping("/employee/search")
	public String search(@RequestParam("s") String s, Model model) {
		if (s.equals("")) {
			return "redirect:/employee";
		}

		model.addAttribute("employees", employeeService.search(s));
		return "list";
	}
	
	@GetMapping("/employee-list")
	public String showlist(Model model) {
		model.addAttribute("employees", employeeService.findAll());
		return "list-employee";
	}
	
	@GetMapping("/employee-list/search")
	public String searchlist(@RequestParam("s") String s, Model model) {
		if (s.equals("")) {
			return "redirect:/employee-list";
		}

		model.addAttribute("employees", employeeService.search(s));
		return "list-employee";
	}
}
