package com.vnpt.demo.repository;


import java.util.List;

import com.vnpt.demo.model.AppRoleDataDetail;


public interface AppRoleDataDetailRepositoryCustom {

	public List<AppRoleDataDetail> getIncludeList(int appRoleDataId) throws Exception;
	public List<AppRoleDataDetail> getExcludeList(int appRoleDataId) throws Exception;
	public List<String> getIncludeValueList(int appRoleDataId) throws Exception;
	public List<String> getExcludeValueList(int appRoleDataId) throws Exception;
}