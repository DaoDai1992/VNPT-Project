package com.vnpt.demo.repository;


import java.util.List;

import com.vnpt.demo.model.MyEntity;

public interface MyEntityRepositoryCustom {
    
    public Long getMaxEmpId();
    
    public MyEntity createEmployee(String name, String phone) throws Exception;
    public MyEntity updateEmployee(int empId, String name, String phone) throws Exception;
    public MyEntity readEmployee(int empId) throws Exception;
    public boolean removeEmployee(int empId) throws Exception;
    public Object callFunctionSingleValue(String inputText);
    public List callFunctionRefCursorValue(int employeeId);
    public List getAllEmployees();
    public Object callStoredProcedureSingleOutputValue(Object inParam);
    public Object callStoredProcedureRefCursorOutputValue(Object inParam);
}