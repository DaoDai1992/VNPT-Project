package com.vnpt.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToOne;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;

@Entity
@Table(name = "employee")
@NamedStoredProcedureQueries({
	   @NamedStoredProcedureQuery(name = "sp_text_length", 
	                              procedureName = "sp_text_length",
	                              parameters = {
	                                 @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_text", type = String.class),
	                                 @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_length", type = Integer.class)
	                              })
	})
@javax.persistence.NamedNativeQueries({
	@javax.persistence.NamedNativeQuery(name = "getAllEmployees", query = "select id,name,phone from employee",resultSetMapping="get_all_employees"),
	@javax.persistence.NamedNativeQuery(name = "fn_employee_address", query = "{? =  call fn_employee_address( ? ) }", hints = {@javax.persistence.QueryHint(name = "org.hibernate.callable", value = "true") })
})
@javax.persistence.SqlResultSetMappings({
	@javax.persistence.SqlResultSetMapping(
			name = "get_all_employees",
			entities = {
				@EntityResult(
						entityClass = Employee.class, 
						fields = { 
							@FieldResult(name = "id", column = "id"),
							@FieldResult(name = "name", column = "name"), 
							@FieldResult(name = "phone", column = "phone") 
						}
				)
			}
	),
	@SqlResultSetMapping(name = "employee_address", 
	entities = {
		@EntityResult(
				entityClass = MyEntity.class, 
				fields = { 
						@FieldResult(name = "id", column = "ID"),
						@FieldResult(name = "name", column = "NAME"), 
						@FieldResult(name = "phone", column = "PHONE")					
				}
		)
		,
		@EntityResult(
				entityClass = Address.class, 
				fields = { 
						@FieldResult(name = "id", column = "address_id"),
						@FieldResult(name = "fullAddress", column = "full_address") 
				}
		),
		@EntityResult(
				entityClass = Country.class, 
				fields = { 
						@FieldResult(name = "id", column = "country_id"),
						@FieldResult(name = "name", column = "country_name") 
				}
		)
	}
)
})
public class MyEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
    @SequenceGenerator(sequenceName = "emp_seq", allocationSize = 1, name = "emp_seq")
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "phone")
	private String phone;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ADDRESS_ID")
	private Address address;
	
	public MyEntity() {
		super();
	}

	public MyEntity(int id, String name, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}
