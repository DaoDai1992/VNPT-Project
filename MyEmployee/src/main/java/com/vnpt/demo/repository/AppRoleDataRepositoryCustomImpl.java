package com.vnpt.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.vnpt.demo.model.AppRoleData;
 
@Repository
public class AppRoleDataRepositoryCustomImpl implements AppRoleDataRepositoryCustom {
	public static final Logger logger = LoggerFactory.getLogger(AppRoleDataRepositoryCustomImpl.class);
	@PersistenceContext
    EntityManager entityManager;
	@SuppressWarnings("unchecked")
	@Override
	public List<AppRoleData> getAppRoleDataListBy(int roleId, String tableName, String crud) throws Exception {
		List<AppRoleData> result = null;
		try {
			String sql = "SELECT a FROM AppRoleData a WHERE a.roleId=:roleId and UPPER(a.tableName)=UPPER(:tableName) and UPPER(a.crud)=UPPER(:crud)";
            Query query = entityManager.createQuery(sql,AppRoleData.class);
            query.setParameter("roleId", roleId);
            query.setParameter("tableName", tableName);
            query.setParameter("crud", crud);
            result = query.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return result;
	}
	@Override
	public AppRoleData getAppRoleDataBy(int roleId, String tableName, String crud) throws Exception {
		List<AppRoleData> list = getAppRoleDataListBy(roleId, tableName, crud);
		if(list==null||list.isEmpty())
			return null;
		return list.get(0);
	}
    
}