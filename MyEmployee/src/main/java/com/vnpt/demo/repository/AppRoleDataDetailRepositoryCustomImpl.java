package com.vnpt.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.vnpt.demo.model.AppRoleDataDetail;


@Repository
public class AppRoleDataDetailRepositoryCustomImpl implements AppRoleDataDetailRepositoryCustom {
	public static final Logger logger = LoggerFactory.getLogger(AppRoleDataDetailRepositoryCustomImpl.class);
	@PersistenceContext
    EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<AppRoleDataDetail> loadIncludeExcludeList(int appRoleDataId,String includeExclude) throws Exception {
		List<AppRoleDataDetail> result = null;
		try {
			String sql = "SELECT a FROM AppRoleDataDetail a WHERE a.appRoleDataId=:appRoleDataId and UPPER(a.includeExclude)=UPPER(:includeExclude)";
            Query query = entityManager.createQuery(sql);
            query.setParameter("appRoleDataId", appRoleDataId).setParameter("includeExclude", includeExclude);
            result = query.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> loadIncludeExcludeValueList(int appRoleDataId,String includeExclude) throws Exception {
		List<String> result = null;
		try {
			String sql = "SELECT a.value FROM AppRoleDataDetail a WHERE a.appRoleDataId=:appRoleDataId and UPPER(a.includeExclude)=UPPER(:includeExclude)";
            Query query = entityManager.createQuery(sql);
            query.setParameter("appRoleDataId", appRoleDataId).setParameter("includeExclude", includeExclude);
            result = query.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return result;
	}
	
	@Override
	public List<AppRoleDataDetail> getExcludeList(int appRoleDataId) throws Exception {
		String includeExclude = "E";
		return loadIncludeExcludeList(appRoleDataId, includeExclude);
	}
	@Override
	public List<String> getIncludeValueList(int appRoleDataId) throws Exception {
		String includeExclude = "I";
		return loadIncludeExcludeValueList(appRoleDataId, includeExclude);
	}
	@Override
	public List<String> getExcludeValueList(int appRoleDataId) throws Exception {
		String includeExclude = "E";
		return loadIncludeExcludeValueList(appRoleDataId, includeExclude);
	}
	@Override
	public List<AppRoleDataDetail> getIncludeList(int appRoleDataId) throws Exception {
		String includeExclude = "I";
		return loadIncludeExcludeList(appRoleDataId, includeExclude);
	}
}
