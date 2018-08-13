package com.vnpt.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vnpt.demo.model.AppRoleData;
import com.vnpt.demo.repository.AppRoleDataRepository;

@Service
public class AppRoleDataServiceImpl implements AppRoleDataService{

	@Autowired
	private AppRoleDataRepository appRoleDataRepository;
	@Override
	public List<AppRoleData> getAppRoleDataListBy(int roleId, String tableName, String crud) throws Exception {
		return appRoleDataRepository.getAppRoleDataListBy(roleId, tableName, crud);
	}

	@Override
	public AppRoleData getAppRoleDataBy(int roleId, String tableName, String crud) throws Exception {
		return appRoleDataRepository.getAppRoleDataBy(roleId, tableName, crud);
	}

	@Override
	public AppRoleDataRepository getAppRoleDataRepository() {
		return appRoleDataRepository;
	}

}
