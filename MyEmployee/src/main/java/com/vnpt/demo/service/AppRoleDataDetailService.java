package com.vnpt.demo.service;

import java.util.List;

import com.vnpt.demo.model.AppRoleDataDetail;
import com.vnpt.demo.repository.AppRoleDataDetailRepository;

public interface AppRoleDataDetailService {
	public List<AppRoleDataDetail> getIncludeList(int appRoleDataId) throws Exception;
	public List<AppRoleDataDetail> getExcludeList(int appRoleDataId) throws Exception;
	public List<String> getIncludeValueList(int appRoleDataId) throws Exception;
	public List<String> getExcludeValueList(int appRoleDataId) throws Exception;
	public AppRoleDataDetailRepository getAppRoleDataDetailRepository();
}
