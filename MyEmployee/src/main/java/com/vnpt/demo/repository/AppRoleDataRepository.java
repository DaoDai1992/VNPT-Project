package com.vnpt.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.vnpt.demo.model.AppRoleData;

public interface AppRoleDataRepository extends CrudRepository<AppRoleData, Integer>,AppRoleDataRepositoryCustom {
}
