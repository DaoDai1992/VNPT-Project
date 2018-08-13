package com.vnpt.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vnpt.demo.repository.AppUserDataRepository;

@Service
public class AppUserDataServiceImpl implements AppUserDataService{

	@Autowired
	private AppUserDataRepository appUserDataRepository;

	@Override
	public List<String> getIncludeExcludeValueListBy(String username, String tableName, String crud,
			String includeExclude) throws Exception {
		return appUserDataRepository.getIncludeExcludeValueListBy(username, tableName, crud, includeExclude);
	}

	@Override
	public List<String> getIncludeValueListBy(String username, String tableName, String crud) throws Exception {
		return appUserDataRepository.getIncludeValueListBy(username, tableName, crud);
	}

	@Override
	public List<String> getExcludeValueListBy(String username, String tableName, String crud) throws Exception {
		return appUserDataRepository.getExcludeValueListBy(username, tableName, crud);
	}

	public AppUserDataRepository getAppUserDataRepository() {
		return appUserDataRepository;
	}

	public void setAppUserDataRepository(AppUserDataRepository appUserDataRepository) {
		this.appUserDataRepository = appUserDataRepository;
	}
	

}
