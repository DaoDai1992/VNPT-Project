package com.vnpt.demo.service;

import java.util.List;

import com.vnpt.demo.model.AppRoleData;
import com.vnpt.demo.repository.AppRoleDataRepository;

public interface AppRoleDataService {
	public List<AppRoleData> getAppRoleDataListBy(int roleId, String tableName, String crud) throws Exception;
	public AppRoleData getAppRoleDataBy(int roleId, String tableName, String crud) throws Exception;
	public AppRoleDataRepository getAppRoleDataRepository();
}
