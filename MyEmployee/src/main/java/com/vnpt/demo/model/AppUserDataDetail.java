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
@Table(name = "app_user_data_detail")
public class AppUserDataDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String INCLUDE = "I";
	public static final String EXCLUDE = "E";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_data_detail_seq")
    @SequenceGenerator(sequenceName = "app_user_data_detail_seq", allocationSize = 1, name = "app_user_data_detail_seq")
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "app_user_data_id", nullable = false)
	private int appUserDataId;
	
	@Column(name = "include_exclude", nullable = false)
	private String includeExclude;
	
	@Column(name = "value")
	private String value;

	public AppUserDataDetail() {
		super();
	}

	public AppUserDataDetail(int id, int appUserDataId, String includeExclude, String value) {
		super();
		this.id = id;
		this.appUserDataId = appUserDataId;
		this.includeExclude = includeExclude;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getAppUserDataId() {
		return appUserDataId;
	}

	public void setAppUserDataId(int appUserDataId) {
		this.appUserDataId = appUserDataId;
	}

}
