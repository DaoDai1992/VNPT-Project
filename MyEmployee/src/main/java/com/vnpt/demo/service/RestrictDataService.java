package com.vnpt.demo.service;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vnpt.demo.model.AppRoleData;
import com.vnpt.demo.model.Role;
import com.vnpt.demo.model.User;
import com.vnpt.demo.repository.RoleRepository;
import com.vnpt.demo.repository.UserRepository;

@Service
public class RestrictDataService {
	public static final Logger logger = LoggerFactory.getLogger(RestrictDataService.class);
	private Map<Class<?>,String> table = null;
	private Map<Class<?>,Method> getIdMethod = null;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AppRoleDataService appRoleDataService;
	@Autowired
	private AppUserDataService appUserDataService;
	@Autowired
	private AppRoleDataDetailService appRoleDataDetailService;
	@Autowired
	private UserRepository userRepository;
	
	public void loadInfo(Class<?> clazz) throws Exception{
		if(clazz==null)
			return;
		Entity entityAnnotation = AnnotationUtils.findAnnotation(clazz, Entity.class);
		Table tableAnnotation = AnnotationUtils.findAnnotation(clazz, Table.class);
		if(entityAnnotation==null)
			throw new Exception(clazz.getName()+" is not annotatiated Entity.");
		if(tableAnnotation==null)
			throw new Exception(clazz.getName()+" is not annotatiated Table.");
		String tableName = tableAnnotation.name();
		String schema = tableAnnotation.schema();
		if(schema!=null&&!schema.equals(""))
			tableName = schema+"."+tableName;
		Method getIdMet = null;
		for(Field field : clazz.getDeclaredFields()) {
			if (!field.isAnnotationPresent(Id.class))
				continue;
			String fieldName = field.getName();
			getIdMet = clazz.getMethod("get"+fieldName.substring(0,1).toUpperCase() + fieldName.substring(1));
		}
		if(getIdMet==null) {
			throw new Exception(clazz.getName()+" has not any Primary key.");
		}
		if(table==null)
			table = new HashMap<Class<?>,String>();
		table.put(clazz, tableName);
		if(getIdMethod==null)
			getIdMethod = new HashMap<Class<?>,Method>();
		getIdMethod.put(clazz, getIdMet);
	}
	public String getTableName(Class<?> clazz) throws Exception{
		String result = null;
		try {
			if(clazz==null)
				return result;
			if(table==null||!table.containsKey(clazz))
				loadInfo(clazz);
			if(table==null||!table.containsKey(clazz))
				throw new Exception("No mapping table for class "+clazz.getName());
			result = table.get(clazz);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return result;
	}
	public Method getGetIdMethod(Class<?> clazz) throws Exception{
		Method result = null;
		try {
			if(clazz==null)
				return result;
			if(getIdMethod==null||!getIdMethod.containsKey(clazz))
				loadInfo(clazz);
			if(getIdMethod==null||!getIdMethod.containsKey(clazz))
				throw new Exception("No mapping table for class "+clazz.getName());
			result = getIdMethod.get(clazz);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return result;
	}
	public static <T> List<T> iteratorToList(Iterator<T> iterator){
		if(iterator==null)
			return null;
		List<T> result = null;
		for(;iterator.hasNext();) {
			if(result==null)
				result = new ArrayList<T>();
			result.add(iterator.next());
		}
		return result;
	} 
	public <T> List<T> restrictIterator(Iterator<T> iterator,String crud) throws Exception{
		List<T> elements = iteratorToList(iterator);
		return restrictList(elements,crud);
	}
	public <T> List<T> restrictList(List<T> elements,String crud) throws Exception{
		try {
			if(elements==null||elements.isEmpty())
				return elements;
			List<String> includeKeys = new ArrayList<String>();
			List<String> excludeKeys = new ArrayList<String>();
			buildIncludeExcludeListByRoles(elements.get(0).getClass(),includeKeys,excludeKeys,crud);
			buildIncludeExcludeListByUser(elements.get(0).getClass(), includeKeys, excludeKeys, crud);
			return restrictList(elements, includeKeys, excludeKeys);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return elements;
	}
	public <T> List<T> restrictList(List<T> elements,List<String> includeKeys, List<String> excludeKeys) throws Exception{
		try {
			Map<String,String> includeMaps = listToMap(includeKeys);
			Map<String,String> excludeMaps = listToMap(excludeKeys);
			Method getIdMethod = null;
			String idValue = null;
			Object idObject = null;
			List<T> removeList = new ArrayList<T>();
			if(includeMaps!=null&&!includeMaps.isEmpty()) {
				for(T element : elements) {
					if(getIdMethod==null)
						getIdMethod = getGetIdMethod(element.getClass());
					idObject = getIdMethod.invoke(element);
					if(idObject==null)
						continue;
					idValue = idObject.toString();
					if(!includeMaps.containsKey(idValue)) 
						removeList.add(element);
				}
			}
			for(T remove : removeList) {
				elements.remove(remove);
			}
			removeList.clear();
			if(excludeMaps!=null&&!excludeMaps.isEmpty()) {
				for(T element : elements) {
					if(getIdMethod==null)
						getIdMethod = getGetIdMethod(element.getClass());
					idObject = getIdMethod.invoke(element);
					if(idObject==null)
						continue;
					idValue = idObject.toString();
					if(excludeMaps.containsKey(idValue))
						removeList.add(element);
				}
			}
			for(T remove : removeList) {
				elements.remove(remove);
			}
			removeList.clear();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return elements;
	}
	public static <T> Map<T,T> listToMap(List<T> elements){
		Map<T,T> result = null;
		if(elements==null||elements.isEmpty())
			return result;
		for(T element : elements) {
			if(result == null)
				result = new HashMap<T,T>();
			result.put(element, element);
		}
		return result;
	}
	public RoleRepository getRoleRepository() {
		return roleRepository;
	}
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	public UserRepository getUserRepository() {
		return userRepository;
	}
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public void checkCrud(String crud) throws Exception{
		if(crud==null)
			crud = AppRoleData.READ;
		String[] types = {AppRoleData.CREATE,AppRoleData.READ,AppRoleData.UPDATE,AppRoleData.DELETE};
		boolean ok = false;
		for(String type : types) {
			if(type.equalsIgnoreCase(crud)) {
				ok = true;
				break;
			}
		}
		if(!ok)
			throw new Exception("Invalid input CRUD type.");
	}
	public String getUsernameLoggedin() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication==null)
			throw new Exception("Cannot get information of currently loggedin user.");
		return authentication.getName();
	}
	public <T> void buildIncludeExcludeListByRoles(Class<T> tClass,List<String> includeList, List<String> excludeList,String crud) throws Exception{
		try {
			if(includeList==null&&excludeList==null)
				return;
			String tableName = getTableName(tClass);
			if(tableName==null)
				return;
			checkCrud(crud);
			String username = getUsernameLoggedin();
			User user = userRepository.findByUsername(username);
			Set<Role> roles = user.getRoles();
			if(roles==null)
				return;
			Role role = null;
			int roleId = -1;
			AppRoleData roleData = null;
			int appRoleDataId = -1;
			List<String> includeListByRole = null;
			List<String> excludeListByRole = null;
			boolean noDupplicate = true;
			for(Iterator<Role> iterator = roles.iterator();iterator.hasNext();) {
				role = iterator.next();
				if(role==null)
					continue;
				roleId = role.getRole_id();
				roleData = appRoleDataService.getAppRoleDataBy(roleId, tableName, crud);
				if(roleData==null)
					continue;
				appRoleDataId = roleData.getId();
				includeListByRole = appRoleDataDetailService.getIncludeValueList(appRoleDataId);
				excludeListByRole = appRoleDataDetailService.getExcludeValueList(appRoleDataId);
				addListToList(includeListByRole, includeList, noDupplicate);
				addListToList(excludeListByRole, excludeList, noDupplicate);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	public <T> void buildIncludeExcludeListByUser(Class<T> tClass,List<String> includeList, List<String> excludeList,String crud) throws Exception{
		try {
			if(includeList==null&&excludeList==null)
				return;
			String tableName = getTableName(tClass);
			if(tableName==null)
				return;
			checkCrud(crud);
			String username = getUsernameLoggedin();
			List<String> includeListByUser = null;
			List<String> excludeListByUser = null;
			boolean noDupplicate = true;
			includeListByUser = appUserDataService.getIncludeValueListBy(username, tableName, crud);
			excludeListByUser = appUserDataService.getExcludeValueListBy(username, tableName, crud);
			addListToList(includeListByUser, includeList, noDupplicate);
			addListToList(excludeListByUser, excludeList, noDupplicate);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	public AppRoleDataService getAppRoleDataService() {
		return appRoleDataService;
	}
	public void setAppRoleDataService(AppRoleDataService appRoleDataService) {
		this.appRoleDataService = appRoleDataService;
	}
	public AppRoleDataDetailService getAppRoleDataDetailService() {
		return appRoleDataDetailService;
	}
	public void setAppRoleDataDetailService(AppRoleDataDetailService appRoleDataDetailService) {
		this.appRoleDataDetailService = appRoleDataDetailService;
	}
	public static <T> void addListToList(List<T> fromList, List<T> toList, boolean noDupplicate) {
		if(fromList==null||fromList.isEmpty()||toList==null)
			return;
		if(!noDupplicate) {
			toList.addAll(fromList);
			return;
		}
		Map<T,T> exist= listToMap(toList);
		for(T t : fromList) {
			if(exist==null)
				exist = new HashMap<T,T>();
			if(exist.containsKey(t))
				continue;
			toList.add(t);
			exist.put(t, t);
		}
	}
	public AppUserDataService getAppUserDataService() {
		return appUserDataService;
	}
	public void setAppUserDataService(AppUserDataService appUserDataService) {
		this.appUserDataService = appUserDataService;
	}
}
