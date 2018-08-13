package com.vnpt.demo.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vnpt.demo.model.UserAttempts;

@Repository
public class UserAttemptsRepositoryImpl implements UserAttemptsRepository {

	public static final Logger logger = LoggerFactory.getLogger(UserAttemptsRepositoryImpl.class);

	@PersistenceContext
	EntityManager entityManager;

	private int saveTimeAccount = 0;
	
	private static final int MAX_ATTEMPTS = 3;

	@Override
	public UserAttempts getUserAttempts(String name) {
		String sql = "Select a.id, a.attempts, a.username , a.lastmodified from USER_ATTEMPTS a where a.username = :username order by ID DESC";
		Query query = entityManager.createNativeQuery(sql, UserAttempts.class);
		query.setParameter("username", name);
		List<UserAttempts> userAttemptsList = query.getResultList();
		if (userAttemptsList.size() > 0){
			System.out.println("dai.dv come in get: " + userAttemptsList.get(0).getUsername() + ", time: " + userAttemptsList.get(0).getLastModified());
			return userAttemptsList.get(0);
		}
		else
			return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateStatusUserAttempts(String username) {
		UserAttempts userAttempt = getUserAttempts(username);
		if (userAttempt == null) {
			System.out.println("come in");
			UserAttempts user = new UserAttempts();
			user.setUsername(username);
			user.setLastModified(new Date());
			user.setAttempts(saveTimeAccount + 1);
			try {
				entityManager.persist(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("dai.dv else: " + userAttempt.getAttempts());
			if (userAttempt.getAttempts() >= MAX_ATTEMPTS) {
				System.out.println("dai.dv else 1");
				String sql = "update APP_USER set ACCOUNTNONLOCKED = 0 where USER_NAME = :username";
				int change = entityManager.createNativeQuery(sql, com.vnpt.demo.model.User.class).setParameter("username", userAttempt.getUsername()).executeUpdate();
				System.out.println("dvdd: " + change);			
			} else {
				UserAttempts user = new UserAttempts();
				user.setUsername(username);
				user.setAttempts(userAttempt.getAttempts() + 1);
				user.setLastModified(new Date());
				try {
					entityManager.persist(user);
				} catch (Exception e) {
					System.out.println("loi them moi");
				}
			}

		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void resetUserAttempts(String name) {
		System.out.println("dai.dv reset " + name);
		UserAttempts user = new UserAttempts();
		user.setUsername(name);
		user.setLastModified(new Date());
		user.setAttempts(saveTimeAccount);
		try {
			entityManager.persist(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
