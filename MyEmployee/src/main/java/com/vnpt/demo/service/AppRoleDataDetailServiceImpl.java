package com.vnpt.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vnpt.demo.model.AppRoleDataDetail;
import com.vnpt.demo.repository.AppRoleDataDetailRepository;

@Service
public class AppRoleDataDetailServiceImpl implements AppRoleDataDetailService{

	@Autowired
	private AppRoleDataDetailRepository appRoleDataDetailRepository;
	@Override
	public List<AppRoleDataDetail> getIncludeList(int appRoleDataId) throws Exception {
		return appRoleDataDetailRepository.getIncludeList(appRoleDataId);
	}

	@Override
	public List<AppRoleDataDetail> getExcludeList(int appRoleDataId) throws Exception {
		return appRoleDataDetailRepository.getExcludeList(appRoleDataId);
	}

	@Override
	public List<String> getIncludeValueList(int appRoleDataId) throws Exception {
		return appRoleDataDetailRepository.getIncludeValueList(appRoleDataId);
	}

	@Override
	public List<String> getExcludeValueList(int appRoleDataId) throws Exception {
		return appRoleDataDetailRepository.getExcludeValueList(appRoleDataId);
	}

	@Override
	public AppRoleDataDetailRepository getAppRoleDataDetailRepository() {
		return appRoleDataDetailRepository;
	}

}
