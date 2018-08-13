package com.vnpt.demo.repository;


import java.util.List;


public interface AppUserDataRepositoryCustom {
	public List<String> getIncludeExcludeValueListBy(String username, String tableName, String crud, String includeExclude) throws Exception;
	public List<String> getIncludeValueListBy(String username, String tableName, String crud) throws Exception;
	public List<String> getExcludeValueListBy(String username, String tableName, String crud) throws Exception;

}