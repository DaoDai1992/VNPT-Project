package com.vnpt.demo.repository;


import java.util.List;

import com.vnpt.demo.model.AppRoleData;

public interface AppRoleDataRepositoryCustom {

	public List<AppRoleData> getAppRoleDataListBy(int roleId, String tableName, String crud) throws Exception;
	public AppRoleData getAppRoleDataBy(int roleId, String tableName, String crud) throws Exception;

}