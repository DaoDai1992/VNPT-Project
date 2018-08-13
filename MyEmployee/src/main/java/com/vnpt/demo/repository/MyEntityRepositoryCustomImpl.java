package com.vnpt.demo.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vnpt.demo.model.Employee;
import com.vnpt.demo.model.MyEntity;
 
@Repository
public class MyEntityRepositoryCustomImpl implements MyEntityRepositoryCustom {
	public static final Logger logger = LoggerFactory.getLogger(MyEntityRepositoryCustomImpl.class);
	@PersistenceContext
    EntityManager entityManager;
    @Override
    public Long getMaxEmpId() {
        try {
            String sql = "SELECT coalesce(max(e.id), 0) FROM Employee e";
            Query query = entityManager.createQuery(sql);
            return (Long) query.getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    public MyEntity updateEmployee(int empId, String name, String phone) throws Exception{
    	MyEntity e = entityManager.find(MyEntity.class, empId);
        if (e == null) {
            return null;
        }
        e.setName(name);
        e.setPhone(phone);
        entityManager.flush();
        return e;
    }
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public MyEntity createEmployee(String name, String phone) throws Exception{
    	MyEntity result = null;
    	try {
			result = new MyEntity();
			result.setName(name);
			result.setPhone(phone);
			entityManager.persist(result);
		} catch (Exception e) {
			logger.error("Loi them Employee: "+e.getMessage());
			throw e;
		}
		return result;
	}
	@Override
	public MyEntity readEmployee(int empId) throws Exception{
		return entityManager.find(MyEntity.class, empId);
	}
	public Session session() {
		return ((Session)this.entityManager.getDelegate());
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean removeEmployee(int empId) throws Exception {
		boolean result = false;
		try {
			MyEntity e = entityManager.find(MyEntity.class, empId);
	        if (e == null) {
	            throw new Exception("EmployeeId: "+empId+" does not exist");
	        }
//	        Employee eNew = new Employee();
//	        eNew.setName("Test2");
//	        eNew.setPhone("Test2");
//			entityManager.persist(eNew);
	        entityManager.remove(e);
	        result=true;
	        return result;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw e;
		}
	}
    // Khong co Native SQL, lay ra gia tri Out
    public Object callStoredProcedureSingleOutputValue(Object inParam) {
    	/* Co the dung 1 trong 2 cach sau:*/
		//* Cach 1: Co Native SQL: Using @NamedStoredProcedureQuery
//		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("sp_text_length").setParameter("p_text", inParam);
//		query.execute();
//		String result = query.getOutputParameterValue("p_length").toString();
		
		//* Cach 2: Goi truc tiep stored procedure trong DB
		StoredProcedureQuery query = entityManager
			    .createStoredProcedureQuery("sp_text_length")
			    .registerStoredProcedureParameter(1, String.class,ParameterMode.IN)
			    .registerStoredProcedureParameter(2, Integer.class,ParameterMode.OUT)
			    .setParameter(1, inParam);
			query.execute();
			String result = query.getOutputParameterValue(2).toString();
		return result;
    }
    // Khong co Native SQL, lay da ref_cursor
    @SuppressWarnings("unchecked")
	public Object callStoredProcedureRefCursorOutputValue(Object param) {
    	StoredProcedureQuery query = entityManager
    		    .createStoredProcedureQuery("sp_get_employee")
    		    .registerStoredProcedureParameter(1, Long.class,ParameterMode.IN)
    		    .registerStoredProcedureParameter(2, Class.class,ParameterMode.REF_CURSOR)
    		    .setParameter(1, Long.parseLong((String) param));
		query.execute();
		List result = query.getResultList();
		logger.info(result.toString());
		return result;
    }
    // Goi Function tra ve single value
    public Object callFunctionSingleValue(String inputText) {
    	Query query = entityManager.createNativeQuery("SELECT fn_text_length(:text) FROM DUAL");
    	query.setParameter("text", inputText);
    	BigDecimal textLength = (BigDecimal) query.getSingleResult();
    	logger.info(textLength.toString());
    	return textLength;
    }
    // Goi Function tra ra ref_cursor: Phai khai bao @NamedNativeQuery o Entity
    @SuppressWarnings("unchecked")
	public List callFunctionRefCursorValue(int employeeId){
		List result = null;
    	try {
    		Query query = entityManager.createNamedQuery("fn_employee_address");
    		query.setParameter(1, employeeId);   		
    		result = query.getResultList();
    		return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
    }
	@SuppressWarnings("unchecked")
	public List getAllEmployees(){
		List result = null;
    	try {
    		// Cach 1: dung named native query
    		Query query = entityManager.createNamedQuery("getAllEmployees");
    		result = query.getResultList();
    		// Cach 2: dung native query
//    		result = (List<Employee>)entityManager.createNativeQuery("select id,name,phone from employee", Employee.class).getResultList(); 
    	} catch (Exception e) {
			logger.error(e.getMessage());
		}
    	return result;
	}
}