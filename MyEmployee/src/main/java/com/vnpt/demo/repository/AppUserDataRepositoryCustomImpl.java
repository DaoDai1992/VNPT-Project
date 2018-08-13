package com.vnpt.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.vnpt.demo.model.AppUserDataDetail;


@Repository
public class AppUserDataRepositoryCustomImpl implements AppUserDataRepositoryCustom{
	public static final Logger logger = LoggerFactory.getLogger(AppUserDataRepositoryCustomImpl.class);
	@PersistenceContext
    EntityManager entityManager;
	@Override
	public List<String> getIncludeValueListBy(String username, String tableName, String crud)
			throws Exception {
		String includeExclude = AppUserDataDetail.INCLUDE;
		return getIncludeExcludeValueListBy(username, tableName, crud, includeExclude);
	}

	@Override
	public List<String> getExcludeValueListBy(String username, String tableName, String crud)
			throws Exception {
		String includeExclude = AppUserDataDetail.EXCLUDE;
		return getIncludeExcludeValueListBy(username, tableName, crud, includeExclude);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getIncludeExcludeValueListBy(String username, String tableName, String crud,
			String includeExclude) throws Exception {
		List<String> result = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT DISTINCT c.value")
			.append(" FROM User a, AppUserData b, AppUserDataDetail c")
			.append(" WHERE UPPER(a.username)=UPPER(:username)")
			.append("    AND UPPER(b.tableName)=UPPER(:tableName)")
			.append("    AND UPPER(b.crud)=UPPER(:crud)")
			.append("    AND UPPER(c.includeExclude)=UPPER(:includeExclude)")
			.append("    AND a.user_id=b.userId")
			.append("    AND b.id = c.appUserDataId");
	        Query query = entityManager.createQuery(sb.toString());
	        query.setParameter("username", username);
	        query.setParameter("tableName", tableName);
	        query.setParameter("crud", crud);
	        query.setParameter("includeExclude",includeExclude);
	        result = query.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return result;
	}
	
}
