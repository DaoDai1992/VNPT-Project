package com.vnpt.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vnpt.demo.model.Employee;
import com.vnpt.demo.model.MyEntity;

public interface MyEntityRepository extends CrudRepository<MyEntity, Integer>,MyEntityRepositoryCustom{
    List<Employee> findByNameContaining(String q);
    /*
     * De chay duoc @Procedure thi bat buoc phai duoc khai bao Native SQL o MyEntity
     * */
    @Procedure(name = "in_only_test")
    void inOnlyTest(@Param("inParam1") String inParam1);
    @Procedure(name = "in_and_out_test")
    String inAndOutTest(@Param("inParam1") String inParam1);
    // Lay ra gia tri rieng le cua function
    @Query(nativeQuery = true, value = "SELECT fn_text_length(:text) FROM DUAL")
    int getTextLength(@Param("text") String text);
}
