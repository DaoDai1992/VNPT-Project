package com.vnpt.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "app_role_data")
public class AppRoleData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String CREATE = "C";
	public static final String READ = "R";
	public static final String UPDATE = "U";
	public static final String DELETE = "D";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_role_data_seq")
    @SequenceGenerator(sequenceName = "app_role_data_seq", allocationSize = 1, name = "app_role_data_seq")
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "role_id", nullable = false)
	private int roleId;
	
	@Column(name = "table_name", nullable = false)
	private String tableName;
	
	@Column(name = "crud")
	private String crud;

	public AppRoleData() {
		super();
	}

	public AppRoleData(int id, int roleId, String tableName, String crud) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.tableName = tableName;
		this.crud = crud;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCrud() {
		return crud;
	}

	public void setCrud(String crud) {
		this.crud = crud;
	}

}
