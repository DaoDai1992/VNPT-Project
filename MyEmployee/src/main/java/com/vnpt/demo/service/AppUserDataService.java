package com.vnpt.demo.service;

import java.util.List;

import com.vnpt.demo.repository.AppUserDataRepository;


public interface AppUserDataService {
	public List<String> getIncludeExcludeValueListBy(String username, String tableName, String crud, String includeExclude) throws Exception;
	public List<String> getIncludeValueListBy(String username, String tableName, String crud) throws Exception;
	public List<String> getExcludeValueListBy(String username, String tableName, String crud) throws Exception;
	public AppUserDataRepository getAppUserDataRepository();
}
