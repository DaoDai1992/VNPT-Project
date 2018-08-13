package com.vnpt.demo.model;

public class Patient {
	private String id;
	private String name;
	private String insurance_code;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInsurance_code() {
		return insurance_code;
	}
	public void setInsurance_code(String insurance_code) {
		this.insurance_code = insurance_code;
	}
	public Patient(String id, String name, String insurance_code) {
		super();
		this.id = id;
		this.name = name;
		this.insurance_code = insurance_code;
	}
	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
