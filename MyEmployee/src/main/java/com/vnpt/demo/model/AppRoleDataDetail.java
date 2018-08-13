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
@Table(name = "app_role_data_detail")
public class AppRoleDataDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String INCLUDE = "I";
	public static final String EXCLUDE = "E";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_role_data_detail_seq")
    @SequenceGenerator(sequenceName = "app_role_data_detail_seq", allocationSize = 1, name = "app_role_data_detail_seq")
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "app_role_data_id", nullable = false)
	private int appRoleDataId;
	
	@Column(name = "include_exclude", nullable = false)
	private String includeExclude;
	
	@Column(name = "value")
	private String value;

	public AppRoleDataDetail() {
		super();
	}

	public AppRoleDataDetail(int id, int appRoleDataId, String includeExclude, String value) {
		super();
		this.id = id;
		this.appRoleDataId = appRoleDataId;
		this.includeExclude = includeExclude;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAppRoleDataId() {
		return appRoleDataId;
	}

	public void setAppRoleDataId(int appRoleDataId) {
		this.appRoleDataId = appRoleDataId;
	}

	public String getIncludeExclude() {
		return includeExclude;
	}

	public void setIncludeExclude(String includeExclude) {
		this.includeExclude = includeExclude;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
